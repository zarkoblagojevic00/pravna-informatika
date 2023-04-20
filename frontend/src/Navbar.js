import { NavLink } from "react-router-dom";
import "./Navbar.css";

export default function Navbar() {
  const getNavLinkStyle = ({ isActive, isPending }) =>
    `navbar-link ${isActive ? "active" : isPending ? "pending" : ""}`;

  return (
    <nav className="navbar">
      <ul className="navbar-list">
        <li className="navbar-item">
          <NavLink to="/law" className={getNavLinkStyle}>
            Zakonik
          </NavLink>
        </li>
        <li className="navbar-item">
          <NavLink to="/judgements" className={getNavLinkStyle}>
            Presude
          </NavLink>
        </li>
        <li className="navbar-item">
          <NavLink to="/create-judgement" className={getNavLinkStyle}>
            Nova presuda
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}
