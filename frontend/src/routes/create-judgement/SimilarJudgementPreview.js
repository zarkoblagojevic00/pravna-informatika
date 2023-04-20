import "./SimilarJudgementPreview.css";

export default function SimilarJudgementPreview({ judgement }) {
  return (
    <div className="card mt-2">
      <div className="card-body">
        <h5 className="card-title mb-3">
          {judgement.poslovniBroj}
          {" | "}
          <span>
            <small>{judgement.sud}</small>
          </span>
          {" | "}
          <span>
            <small>Sudija {judgement.sudija}</small>
          </span>
        </h5>
        <p className="card-text">
          <span className="judgement-tag">
            Sličnost: {parseFloat(judgement.slicnost).toFixed(2)}
          </span>
          <span className="judgement-tag">{judgement.krivicnoDelo}</span>
          <span className="judgement-tag">
            Vrednost: {judgement.vrednost} &euro;
          </span>
          {judgement.nasilno === "da" && (
            <span className="judgement-tag">Nasilno</span>
          )}
          {judgement.nepogoda === "da" && (
            <span className="judgement-tag">Nepogoda</span>
          )}
          {judgement.umisljaj === "da" && (
            <span className="judgement-tag">Umisljaj</span>
          )}
        </p>
        <a href={`judgements/${judgement.id}`} className="btn btn-primary">
          Detaljnije
        </a>
      </div>
    </div>
  );
}
