import React from "react";
import { QueryClient, QueryClientProvider } from "react-query";
import NewsPageAppBar from "./components/NewsPageAppBar";
import { createTheme, ThemeProvider } from "@mui/material";
import { SearchDto } from "./entities/SearchDto";
import { Content } from "./components/Content";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import NewsDetails from "./components/News/NewsDetails";

const queryClient = new QueryClient();
export default function App() {
  const [search, setSearch] = React.useState(new SearchDto());

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

  const searchHandler = (content: string) => {
    setSearch(new SearchDto(content));
  };

  return (
    <div>
      <ThemeProvider theme={customTheme}>
        <NewsPageAppBar searchString={searchHandler} />
        <QueryClientProvider client={queryClient}>
          <Router>
            <Routes>
              <Route path="/" element={<Content searchString={search} />} />
              <Route path="/news/:id" element={<NewsDetails />} />
            </Routes>
          </Router>
        </QueryClientProvider>
      </ThemeProvider>
    </div>
  );
}
