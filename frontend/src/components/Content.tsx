import { SearchDto } from "../entities/SearchDto";
import React from "react";
import { useQuery } from "react-query";
import axios from "axios";
import NewsCards from "./News/NewsCards";

type ContentProps = {
  search: SearchDto;
};

export function Content(props: ContentProps): JSX.Element {
  const { search } = props;

  const [dataState, setDateState] = React.useState();

  const { isLoading, error, data } = useQuery(["news", search], async () => {
    axios("http://localhost:3000/api/v1/news/search", { params: search }).then(
      function (res) {
        console.log(res);
        console.log(res.data);
        setDateState(res.data);
        return res.data;
      }
    );
  });

  if (isLoading) return <div>Loading ...</div>;
  if (error) {
    return <div>'An error has occurred: ' + error.message</div>;
  }
  return dataState ? <NewsCards news={dataState} /> : <div />;
}
