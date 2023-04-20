import "./JudgementsRoot.css";

import JudgementFeatures from "./JudgementFeatures";
import { useEffect, useState } from "react";
import {
  getJudgementNames,
  getJudgementFeatures,
} from "../../services/document-service";
import { NavLink, Outlet } from "react-router-dom";

export default function JudgementsRoot() {
  const [judgementNames, setJudgementNames] = useState([]);
  const [selectedJudgement, setSelectedJudgement] = useState("");
  const [judgmentFeatures, setJudgementFeatures] = useState({});
  const featuresLoaded = Object.keys(judgmentFeatures).length > 0;

  const selectedJudgementFeatures =
    featuresLoaded > 0
      ? judgmentFeatures[(parseInt(selectedJudgement) - 1).toString()]
      : null;

  useEffect(() => {
    if (window.location.href) {
      const idx = window.location.href.lastIndexOf("/");
      setTimeout(
        () => setSelectedJudgement(window.location.href.slice(idx + 1)),
        300
      );
    }
  }, []);
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

  return (
    <div className="d-flex h-100">
      <Outlet></Outlet>
      <div className="judgements-sidebar">
        <div className="title-header">Pregled presuda</div>
        <div className="sidebar-content h-100">
          <div className="my-2 overflow-auto" style={{ height: "20%" }}>
            {judgementNames &&
              judgementNames.map((name) => (
                <li key={name} className="j-nav-item">
                  <NavLink
                    onClick={() => setSelectedJudgement(name)}
                    className="j-nav-link"
                    to={`${name}`}
                  >
                    Presuda {name}
                  </NavLink>
                </li>
              ))}
          </div>
          {selectedJudgement && featuresLoaded && (
            <JudgementFeatures features={selectedJudgementFeatures} />
          )}
        </div>
      </div>
    </div>
  );
}
