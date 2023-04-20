import { useState } from "react";
// import {
//   getJudgementNames,
//   getJudgementHTML,
//   getJudgementFeatures,
// } from "../../services/document-service";
// import { Autocomplete, TextField } from "@mui/material";
import "./CreateJudgementRoot.css";
import {
  getReasoningResult,
  createJudgement,
} from "../../services/reasoning-service";
import { Button } from "@mui/material";
import SimilarJudgementPreview from "./SimilarJudgementPreview";

function getRandomInt(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

const initReasoningForm = {
  okrivljeni: "I.R.",
  krivicnoDelo: "Teška krađa",
  vrednost: 1000,
  nasilno: "ne",
  umisljaj: "ne",
  nepogoda: "da",
};

const initResidualForm = {
  id: getRandomInt(1000, 50000),
  sud: "",
  poslovniBroj: "",
  sudija: "",
  tuzilac: "",
  vrstaPresude: "novcana",
  kazna: 0,
  primenjeniPropisi: [],
};

export default function JudgementsRoot() {
  const [reasoningForm, setReasoningForm] = useState(initReasoningForm);

  const [residualForm, setResidualForm] = useState(initResidualForm);

  const [isResidualFormVisible, setIsResidualFormVisible] = useState(false);
  const [ruleBasedAnswer, setRuleBasedAnswer] = useState("");
  const [caseBasedAnswer, setCaseBasedAnswer] = useState([]);

  const parseCases = (strArr) =>
    strArr.map((strCase) =>
      strCase.split(/,(?![^[]*])/g).reduce((res, prop) => {
        const [propKey, propValue] = prop.split("=");
        res[propKey.trim()] = propValue.trim();
        return res;
      }, {})
    );

  const submitForReasoning = () => {
    getReasoningResult(reasoningForm)
      .then((reasoningResult) => {
        setRuleBasedAnswer(reasoningResult.appliedProvisions);
        const cases = parseCases(reasoningResult.similarCases);
        console.log(cases);
        setCaseBasedAnswer(cases);
        setIsResidualFormVisible(true);
      })
      .catch(console.error);
  };

  const submitJudgement = () => {
    createJudgement({ ...reasoningForm, ...residualForm })
      .then(() => {
        setReasoningForm(initReasoningForm);
        setResidualForm(initResidualForm);
        setIsResidualFormVisible(true);
      })
      .catch(console.error);
  };

  return (
    <div className="d-flex h-100">
      <div className="d-flex col">
        <div className="w-50 html-preview">
          <div className="create-title-header">Unesite novi slučaj </div>
          <div className="d-flex">
            <div className="w-50 h-100 border border-primary">
              {!isResidualFormVisible && (
                <Button variant="contained" onClick={submitForReasoning}>
                  Rezonuj
                </Button>
              )}
            </div>
            <div className="w-50">
              {isResidualFormVisible && (
                <div>
                  <Button variant="contained" onClick={submitJudgement}>
                    Dodaj novu presudu
                  </Button>
                </div>
              )}
            </div>
          </div>
        </div>

        <div className="w-50 html-preview">
          <div className="h-25 p-3 border-bottom">
            <div className="create-title-header">
              Rezultati zaključivanja po pravilima
            </div>
            {ruleBasedAnswer && <div>{ruleBasedAnswer}</div>}
          </div>
          <div className="h-75 p-3">
            <div className="create-title-header">
              Rezultati zaključivanja po slučajevima
            </div>
            <small className="my-3">
              prikazano je 5 najsličnijih slučajeva poređanih od najsličnijeg ka
              manje sličnima
            </small>
            <div className="similar-jdg-container overflow-auto">
              {caseBasedAnswer.map((similarCase) => (
                <SimilarJudgementPreview
                  key={similarCase.id}
                  judgement={similarCase}
                />
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
