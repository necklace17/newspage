import { SearchParams } from "../entities/SearchParams";
import React from "react";
import { useQuery } from "react-query";
import axios from "axios";
import { useLocation } from "react-router-dom";
import NewsCards from "./News/NewsCards";

type ContentProps = {
  searchString: string;
};

function useQueryParams() {
  const { search } = useLocation();
  return React.useMemo(() => new URLSearchParams(search), [search]);
}

export function Content(props: ContentProps): JSX.Element {
  let query = useQueryParams();

  let searchParams = new SearchParams(
    props.searchString,
    parseInt(query.get("searchString") || "1")
  );

  const [dataState, setDateState] = React.useState();

  const { isLoading, error } = useQuery(
    ["news", searchParams.searchString],
    async () => {
      axios("http://localhost:3000/api/v1/news/search", {
        params: searchParams,
      }).then(function (res) {
        setDateState(res.data);
      });
    }
  );

  if (dataState === undefined) {
    return <div>Could not load data</div>;
  }

  if (isLoading) return <div>Loading ...</div>;
  if (error) {
    return <div>'An error has occurred: ' + error.message</div>;
  }
  return dataState ? <NewsCards news={dataState} /> : <div />;
}
