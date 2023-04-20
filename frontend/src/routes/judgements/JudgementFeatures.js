import "./JudgementFeatures.css";

export default function JudgementFeatures({ features }) {
  return (
    <div className="h-100">
      <div className="features-title">Detalji slučaja</div>
      <div className="overflow-auto h-100">
        {features &&
          Object.entries(features)
            .filter(([key]) => key !== "#id")
            .map(([key, value]) => (
              <div key={key}>
                <span className="feature-key">{key.replace("#", "")}</span>{" "}
                <span className="feature-value">{value}</span>
              </div>
            ))}
      </div>
    </div>
  );
}
