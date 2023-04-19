import App from "../App";
import CreateJudgementRoot from "./create-judgement/CreateJudgementRoot";
import ErrorPage from "./ErrorPage";
import JudgementsRoot from "./judgements/JudgementsRoot";
import LawRoot from "./law/LawRoot";
import NotFound from "./NotFound";

const routes = [
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        index: true,
        element: <LawRoot />,
      },
      {
        path: "law",
        element: <LawRoot />,
      },
      {
        path: "judgements",
        element: <JudgementsRoot />,
      },
      {
        path: "create-judgement",
        element: <CreateJudgementRoot />,
      },
      {
        path: "*",
        element: <NotFound />,
      },
    ],
  },
];

export default routes;
