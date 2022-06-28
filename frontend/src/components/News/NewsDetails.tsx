import { useParams } from "react-router-dom";
import { useQuery } from "react-query";
import axios from "axios";
import { Grid } from "@mui/material";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import React from "react";

export default function NewsDetails() {
  let { id } = useParams();

  const { isLoading, error, data } = useQuery(["news"], async () =>
    axios("http://localhost:3000/api/v1/news/" + id).then(function (res) {
      console.log(res.data);
      return res.data;
    })
  );
  if (isLoading) return <div>Loading ...</div>;
  if (error) {
    return <div>'An error has occurred: ' + error.message</div>;
  }

  return (
    <Grid container spacing={1} sx={{ p: 5, pr: 16 }}>
      <Grid item xs={12}>
        <Box>
          <Box sx={{ display: "flex", justifyContent: "center" }}>
            <Typography variant="h5">{data.title}</Typography>
          </Box>
          <Box sx={{ display: "flex", flexDirection: "row-reverse" }}>
            <Typography variant="subtitle1">{data.author}</Typography>
          </Box>
        </Box>
      </Grid>
      <Grid item xs={12}>
        <Typography variant="body1">{data.content}</Typography>
      </Grid>
    </Grid>
  );
}
