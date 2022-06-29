import { SearchDto } from "../entities/SearchDto";
import React from "react";
import { useQuery } from "react-query";
import axios from "axios";
import NewsCards from "./News/NewsCards";

type ContentProps = {
  searchString: SearchDto;
};

export function Content(props: ContentProps): JSX.Element {
  const { searchString } = props;

  const [dataState, setDateState] = React.useState();

  const { isLoading, error } = useQuery(["news", searchString], async () => {
    axios("http://localhost:3000/api/v1/news/search", {
      params: searchString,
    }).then(function (res) {
      console.log(res);
      console.log(res.data);
      setDateState(res.data);
      return res.data;
    });
  });

  if (dataState === undefined) {
    return <div>Could not load data</div>;
  }

  if (isLoading) return <div>Loading ...</div>;
  if (error) {
    return <div>'An error has occurred: ' + error.message</div>;
  }
  return dataState ? <NewsCards news={dataState} /> : <div />;
}
