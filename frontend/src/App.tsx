import React from "react";
import { QueryClient, QueryClientProvider, useQuery } from "react-query";
import { News } from "./entities/News";
import NewsCards from "./components/News/NewsCards";
import NewsPageAppBar from "./components/NewsPageAppBar";
import { createTheme, ThemeProvider } from "@mui/material";
import { SearchDto } from "./entities/SearchDto";

const queryClient = new QueryClient();
export default function App() {
  const [search, setSearch] = React.useState(new SearchDto("", "hi"));

  const customTheme = createTheme({
    components: {
      MuiAppBar: {
        styleOverrides: {
          colorPrimary: { backgroundColor: "#212121" },
          root: { justifyContent: "space-between" },
        },
      },
      MuiToolbar: {
        styleOverrides: {
          root: { justifyContent: "space-between" },
        },
      },
    },
  });

  const handler = (content: string) => {
    setSearch(new SearchDto("", content));
  };

  return (
    <div>
      <ThemeProvider theme={customTheme}>
        <NewsPageAppBar searchString={handler} />
        <div>{search.content}</div>
        <QueryClientProvider client={queryClient}>
          <Example />
        </QueryClientProvider>
      </ThemeProvider>
    </div>
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
