export default function DisplayPDF({ url }) {
  return (
    <embed
      src={url}
      frameBorder="0"
      type="application/pdf"
      width="100%"
      height="100%"
    ></embed>
  );
}
