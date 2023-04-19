import DisplayPDF from "../DisplayPDF";
import { useEffect, useState } from "react";
import { getLawHTML } from "../../services/document-service";
import { getApiUrl } from "../../util";

const apiUrl = getApiUrl();

export default function LawRoot() {
  const [lawHTML, setLawHTML] = useState("");

  useEffect(() => {
    if (window.location.href) {
      const elId = window.location.href.split("#")[1];
      setTimeout(() => {
        const element = document.getElementById(elId);
        console.log(element);
        if (element) element.scrollIntoView();
      }, 400);
    }
  }, []);

  useEffect(() => {
    getLawHTML()
      .then((data) => {
        setLawHTML(data);
      })
      .catch(console.error);
  }, []);

  return (
    <div className="d-flex h-100">
      <div className="d-flex col">
        {lawHTML && (
          <div className="w-50 pdf-preview">
            <DisplayPDF url={`${apiUrl}documents/law/pdf`} />
          </div>
        )}
        <div className="w-50 html-preview text-center">
          <div className="title-header">Zakon</div>
          <div
            className="html-content"
            dangerouslySetInnerHTML={{ __html: lawHTML }}
          ></div>
        </div>
      </div>
    </div>
  );
}
