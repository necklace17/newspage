import { News } from "../../entities/News";
import React from "react";
import NewsCard from "../NewsCard/NewsCard";

type NewsCardProps = {
  news: News[];
};

export default function NewsCards(props: NewsCardProps) {
  const { news } = props;
  return (
    <div>
      {news.map((news) => (
        <NewsCard {...news} />
      ))}
    </div>
  );
}
