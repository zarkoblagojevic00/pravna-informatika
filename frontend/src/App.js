import { Outlet } from "react-router-dom";
import "./App.css";
import Navbar from "./Navbar";

export default function App() {
  return (
    <div className="w-100 h-100 d-flex flex-column">
      <Navbar />
      <div className="col">
        <Outlet />
      </div>
    </div>
  );
}
