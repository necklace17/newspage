import { News } from "../../entities/News";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";

export default function NewsCard(props: News) {
  const { id, title, publication, author, content } = props;
  return (
    <Card sx={{ minWidth: 275, m: 2 }}>
      <CardContent>
        <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
          {author}
        </Typography>
        <Typography variant="h5" component="h2">
          {title}
        </Typography>
        <Typography variant="body2" component="p">
          {content.slice(0, 700)}...
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small">Read</Button>
      </CardActions>
    </Card>
  );
}
