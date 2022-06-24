import React from "react";
import "./App.css";
import {QueryClient, QueryClientProvider, useQuery} from "react-query";
import {News} from "./components/entities/News";
import NewsCard from "./components/news/News";
// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.tsx</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }
const queryClient = new QueryClient();
export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Example />
    </QueryClientProvider>
  );
}

function Example(): JSX.Element {
  const { isLoading, error, data } = useQuery<News[], Error>("repoData", () =>
    fetch("http://localhost:3000/api/v1/news").then((res) => res.json())
  );

  if (isLoading) return <div>Loading ...</div>;
  if (error) {
    // @ts-ignore
    return <div>'An error has occurred: ' + error.message</div>;
  }
  return data ? (
    <div>
      {data.map((news) => (
        <NewsCard {...news} />
      ))}
    </div>
  ) : (
    <div />
  );
}
