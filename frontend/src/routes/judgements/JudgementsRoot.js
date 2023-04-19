import "./JudgementsRoot.css";
import DisplayPDF from "../DisplayPDF";
import JudgementFeatures from "./JudgementFeatures";
import { useEffect, useState } from "react";
import {
  getJudgementNames,
  getJudgementHTML,
  getJudgementFeatures,
} from "../../services/document-service";
import { getApiUrl } from "../../util";
import { Autocomplete, TextField } from "@mui/material";

const apiUrl = getApiUrl();

export default function JudgementsRoot() {
  const [judgementNames, setJudgementNames] = useState([]);
  const [selectedJudgement, setSelectedJudgement] = useState("");
  const [judgementHTML, setJudgementHTML] = useState("");
  const [judgmentFeatures, setJudgementFeatures] = useState({});
  const featuresLoaded = Object.keys(judgmentFeatures).length > 0;

  const selectedJudgementFeatures =
    featuresLoaded > 0
      ? judgmentFeatures[(parseInt(selectedJudgement) - 1).toString()]
      : null;

  useEffect(() => {
    getJudgementNames()
      .then((data) => {
        setJudgementNames(data);
        setSelectedJudgement(data[0]);
      })
      .catch(console.error);

    getJudgementFeatures()
      .then((data) => {
        setJudgementFeatures(
          data.reduce((obj, features) => {
            obj[features["#id"]] = features;
            return obj;
          }, {})
        );
      })
      .catch(console.error);
  }, []);

  useEffect(() => {
    if (!selectedJudgement) return;
    getJudgementHTML(selectedJudgement)
      .then((html) => {
        setJudgementHTML(html);
      })
      .catch(console.error);
  }, [selectedJudgement]);

  return (
    <div className="d-flex h-100">
      <div className="d-flex col">
        {selectedJudgement && (
          <div className="w-50 pdf-preview">
            <DisplayPDF
              url={`${apiUrl}documents/judgement/pdf/${selectedJudgement}`}
            />
          </div>
        )}
        <div className="w-50 html-preview text-center">
          <div className="title-header">Presuda {selectedJudgement}</div>
          <div
            className="html-content"
            dangerouslySetInnerHTML={{ __html: judgementHTML }}
          ></div>
        </div>
      </div>
      <div className="judgements-sidebar">
        <div className="title-header">Pregled presuda</div>
        <div className="sidebar-content">
          <div className="my-2">
            <Autocomplete
              disablePortal
              disableClearable
              id="combo-box-demo"
              options={judgementNames}
              renderInput={(params) => (
                <TextField {...params} label="Odaberite presudu" />
              )}
              getOptionLabel={(option) => `Presuda ${option}`}
              value={selectedJudgement}
              onChange={(event, newValue) => {
                setSelectedJudgement(newValue);
              }}
            />
          </div>
          {selectedJudgement && featuresLoaded && (
            <JudgementFeatures features={selectedJudgementFeatures} />
          )}
        </div>
      </div>
    </div>
  );
}
