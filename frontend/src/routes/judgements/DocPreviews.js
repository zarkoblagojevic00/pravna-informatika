import { useLoaderData } from "react-router-dom";
import { getJudgementHTML } from "../../services/document-service";
import { getApiUrl } from "../../util";
import DisplayPDF from "../DisplayPDF";
const apiUrl = getApiUrl();

export async function loader({ params: { judgementId } }) {
  const judgementHTML = await getJudgementHTML(judgementId);
  return { judgementHTML, judgementId };
}

export default function DocPreviews() {
  const { judgementHTML, judgementId } = useLoaderData();
  return (
    <div className="d-flex col">
      {judgementId && (
        <div className="w-50 pdf-preview">
          <DisplayPDF url={`${apiUrl}documents/judgement/pdf/${judgementId}`} />
        </div>
      )}
      <div className="w-50 html-preview text-center">
        <div className="title-header">Presuda {judgementId}</div>
        <div
          className="html-content"
          dangerouslySetInnerHTML={{ __html: judgementHTML }}
        ></div>
      </div>
    </div>
  );
}
