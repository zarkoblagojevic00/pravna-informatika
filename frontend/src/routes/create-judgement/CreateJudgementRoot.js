import React, { useState } from "react";

import { Autocomplete, Snackbar, TextField, Button } from "@mui/material";

import "./CreateJudgementRoot.css";
import {
  getReasoningResult,
  createJudgement,
} from "../../services/reasoning-service";
import SimilarJudgementPreview from "./SimilarJudgementPreview";
import LoadingSpinner from "../../LoadingSpinner";

import MuiAlert from "@mui/material/Alert";

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

function getRandomInt(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

const getInitReasoningForm = () => ({
  okrivljeni: "",
  krivicnoDelo: "Teška krađa",
  vrednost: 0,
  nasilno: "ne",
  umisljaj: "ne",
  nepogoda: "ne",
});

const getInitResidualForm = () => ({
  id: getRandomInt(1000, 50000),
  sud: "",
  poslovniBroj: "",
  sudija: "",
  tuzilac: "",
  vrstaPresude: "novcana",
  kazna: 0,
  primenjeniPropisi: [],
});

const separator = ";";

export default function JudgementsRoot() {
  const [reasoningForm, setReasoningForm] = useState(getInitReasoningForm());

  const [residualForm, setResidualForm] = useState(getInitResidualForm());

  const [isResidualFormVisible, setIsResidualFormVisible] = useState(false);
  const [ruleBasedAnswer, setRuleBasedAnswer] = useState("");
  const [caseBasedAnswer, setCaseBasedAnswer] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const parseCases = (strArr) =>
    strArr.map((strCase) =>
      strCase.split(/,(?![^[]*])/g).reduce((res, prop) => {
        const [propKey, propValue] = prop.split("=");
        res[propKey.trim()] = propValue.trim();
        return res;
      }, {})
    );

  const submitForReasoning = () => {
    setIsLoading(true);
    getReasoningResult(reasoningForm)
      .then((reasoningResult) => {
        setRuleBasedAnswer(reasoningResult.appliedProvisions);
        const cases = parseCases(reasoningResult.similarCases);
        setCaseBasedAnswer(cases);
        setIsResidualFormVisible(true);
        setIsLoading(false);
      })
      .catch(console.error);
  };

  const submitJudgement = () => {
    createJudgement({ ...reasoningForm, ...residualForm })
      .then(() => {
        console.log("retrieved successfully");
      })
      .catch(console.error)
      .finally(() => {
        setIsResidualFormVisible(false);
        setRuleBasedAnswer("");
        setCaseBasedAnswer([]);
        setReasoningForm(() => getInitReasoningForm());
        setResidualForm(() => getInitResidualForm());
        setAlert(true);
      });
  };

  const daNeOptions = ["da", "ne"];
  const krivicnoDeloOptions = ["Teška krađa", "Krađa"];
  const vrstaPresudeOptions = ["zatvorska", "novcana"];

  const [alert, setAlert] = React.useState(false);

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }

    setAlert(false);
  };

  return (
    <div className="d-flex h-100">
      <div className="d-flex col">
        <div className="w-50 html-preview p-3">
          <div className="create-title-header">Unesite novi slučaj </div>
          <div className="d-flex">
            <div className="w-50 h-100 mx-2">
              <div>
                <form>
                  <TextField
                    disabled={isResidualFormVisible}
                    sx={{ marginBottom: "15px" }}
                    fullWidth
                    label="Okrivljeni"
                    variant="outlined"
                    value={reasoningForm.okrivljeni}
                    onChange={(event) => {
                      setReasoningForm({
                        ...reasoningForm,
                        okrivljeni: event.target.value,
                      });
                    }}
                  />

                  <Autocomplete
                    disabled={isResidualFormVisible}
                    sx={{ marginBottom: "15px" }}
                    disablePortal
                    disableClearable
                    options={krivicnoDeloOptions}
                    fullWidth
                    renderInput={(params) => (
                      <TextField {...params} label="Krivično delo" />
                    )}
                    value={reasoningForm.krivicnoDelo}
                    onChange={(event, newValue) => {
                      setReasoningForm({
                        ...reasoningForm,
                        krivicnoDelo: newValue,
                      });
                    }}
                  />

                  <TextField
                    disabled={isResidualFormVisible}
                    sx={{ marginBottom: "15px" }}
                    fullWidth
                    label="Vrednost ukradenog (u eurima)"
                    variant="outlined"
                    type="number"
                    InputProps={{ inputProps: { min: 0, max: 300000 } }}
                    value={reasoningForm.vrednost}
                    onChange={(event) => {
                      setReasoningForm({
                        ...reasoningForm,
                        vrednost: event.target.value,
                      });
                    }}
                  />

                  <Autocomplete
                    disabled={isResidualFormVisible}
                    sx={{ marginBottom: "15px" }}
                    disablePortal
                    disableClearable
                    options={daNeOptions}
                    fullWidth
                    renderInput={(params) => (
                      <TextField {...params} label="Upotreba sile" />
                    )}
                    value={reasoningForm.nasilno}
                    onChange={(event, newValue) => {
                      setReasoningForm({
                        ...reasoningForm,
                        nasilno: newValue,
                      });
                    }}
                  />

                  <Autocomplete
                    disabled={isResidualFormVisible}
                    sx={{ marginBottom: "15px" }}
                    disablePortal
                    disableClearable
                    options={daNeOptions}
                    fullWidth
                    renderInput={(params) => (
                      <TextField {...params} label="Nepogoda" />
                    )}
                    value={reasoningForm.nepogoda}
                    onChange={(event, newValue) => {
                      setReasoningForm({
                        ...reasoningForm,
                        nepogoda: newValue,
                      });
                    }}
                  />

                  <Autocomplete
                    disabled={isResidualFormVisible}
                    sx={{ marginBottom: "15px" }}
                    disablePortal
                    disableClearable
                    options={daNeOptions}
                    fullWidth
                    renderInput={(params) => (
                      <TextField {...params} label="Umišljaj" />
                    )}
                    value={reasoningForm.umisljaj}
                    onChange={(event, newValue) => {
                      setReasoningForm({
                        ...reasoningForm,
                        umisljaj: newValue,
                      });
                    }}
                  />

                  <div className="mt-3">
                    <Button
                      disabled={isResidualFormVisible}
                      variant="contained"
                      onClick={submitForReasoning}
                      sx={{ textTransform: "none" }}
                    >
                      Rezonuj
                    </Button>
                  </div>
                </form>
              </div>
            </div>
            <div className="w-50 h-100 mx-2">
              {isResidualFormVisible && (
                <form>
                  <TextField
                    sx={{ marginBottom: "15px" }}
                    fullWidth
                    label="Sud"
                    variant="outlined"
                    value={residualForm.sud}
                    onChange={(event) => {
                      setResidualForm({
                        ...residualForm,
                        sud: event.target.value,
                      });
                    }}
                  />

                  <TextField
                    sx={{ marginBottom: "15px" }}
                    fullWidth
                    label="Poslovni broj"
                    variant="outlined"
                    value={residualForm.poslovniBroj}
                    onChange={(event) => {
                      setResidualForm({
                        ...residualForm,
                        poslovniBroj: event.target.value,
                      });
                    }}
                  />

                  <TextField
                    sx={{ marginBottom: "15px" }}
                    fullWidth
                    label="Sudija"
                    variant="outlined"
                    value={residualForm.sudija}
                    onChange={(event) => {
                      setResidualForm({
                        ...residualForm,
                        sudija: event.target.value,
                      });
                    }}
                  />

                  <TextField
                    sx={{ marginBottom: "15px" }}
                    fullWidth
                    label="Tužilac"
                    variant="outlined"
                    value={residualForm.tuzilac}
                    onChange={(event) => {
                      setResidualForm({
                        ...residualForm,
                        tuzilac: event.target.value,
                      });
                    }}
                  />

                  <Autocomplete
                    sx={{ marginBottom: "15px" }}
                    disablePortal
                    disableClearable
                    options={vrstaPresudeOptions}
                    fullWidth
                    renderInput={(params) => (
                      <TextField {...params} label="Vrsta presude" />
                    )}
                    value={residualForm.vrstaPresude}
                    onChange={(event, newValue) => {
                      setResidualForm({
                        ...residualForm,
                        vrstaPresude: newValue,
                      });
                    }}
                  />

                  <TextField
                    sx={{ marginBottom: "15px" }}
                    fullWidth
                    type="number"
                    InputProps={{ inputProps: { min: 0, max: 300000 } }}
                    label={
                      residualForm.vrstaPresude === "novcana"
                        ? "Novcana kazna (u eurima)"
                        : "Kazna zatvorom (u godinama)"
                    }
                    variant="outlined"
                    value={residualForm.kazna}
                    onChange={(event) => {
                      setResidualForm({
                        ...residualForm,
                        kazna: event.target.value,
                      });
                    }}
                  />

                  <TextField
                    fullWidth
                    label="Primenjeni propisi"
                    variant="outlined"
                    value={residualForm.primenjeniPropisi}
                    onChange={(event) => {
                      setResidualForm({
                        ...residualForm,
                        primenjeniPropisi: event.target.value.split(separator),
                      });
                    }}
                  />
                  <p>
                    <small>
                      (odvojiti sa {separator} npr. KZ,cl.239{separator}{" "}
                      KZ,cl.240)
                    </small>
                  </p>

                  <div>
                    <Button
                      variant="contained"
                      onClick={submitJudgement}
                      sx={{ textTransform: "none" }}
                    >
                      Dodaj novu presudu
                    </Button>
                  </div>
                </form>
              )}
            </div>
          </div>
        </div>

        <div className="w-50 html-preview">
          <div className="h-25 p-3 border-bottom">
            <div className="create-title-header">
              Rezultati rasuđivanja po pravilima
            </div>
            {isLoading && <LoadingSpinner />}
            {ruleBasedAnswer && !isLoading && <div>{ruleBasedAnswer}</div>}
          </div>
          <div className="h-75 p-3">
            <div className="create-title-header">
              Rezultati rasuđivanja po slučajevima
            </div>
            {isLoading && <LoadingSpinner />}
            {caseBasedAnswer && !isLoading && (
              <>
                <small className="my-3">
                  prikazano je 5 najsličnijih slučajeva poređanih od
                  najsličnijeg ka manje sličnima
                </small>
                <div className="similar-jdg-container overflow-auto">
                  {caseBasedAnswer.map((similarCase) => (
                    <SimilarJudgementPreview
                      key={similarCase.id}
                      judgement={similarCase}
                    />
                  ))}
                </div>
              </>
            )}
          </div>
        </div>
      </div>
      <Snackbar open={alert} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="success" sx={{ width: "100%" }}>
          Nova presuda uspešno snimljena.
        </Alert>
      </Snackbar>
    </div>
  );
}
