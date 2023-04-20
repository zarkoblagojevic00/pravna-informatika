import App from "../App";
import CreateJudgementRoot from "./create-judgement/CreateJudgementRoot";
import ErrorPage from "./ErrorPage";
import DocPreviews, { loader as docLoader } from "./judgements/DocPreviews";
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
        children: [
          {
            index: true,
            element: (
              <div className="col d-flex justify-content-center align-items-center">
                <h2>Nijedna presuda nije odabrana...</h2>
              </div>
            ),
          },
          {
            path: ":judgementId",
            element: <DocPreviews />,
            loader: docLoader,
          },
        ],
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
