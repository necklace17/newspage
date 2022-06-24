import React from "react";
import "./App.css";
import { QueryClient, QueryClientProvider, useQuery } from "react-query";
import { News } from "./entities/News";
import NewsCards from "./components/NewsCards/NewsCards";

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
    return <div>'An error has occurred: ' + error.message</div>;
  }
  return data ? <NewsCards news={data} /> : <div />;
}
