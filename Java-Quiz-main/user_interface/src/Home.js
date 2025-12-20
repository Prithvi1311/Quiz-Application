import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { UserContext } from "./userContext";
import { Link } from "react-router-dom";
import "./style/Home.css";
import "react-toastify/dist/ReactToastify.css";

const Home = () => {
  const { user, setUser } = useContext(UserContext);
  const [quizes, setQuizes] = useState([]);

  useEffect(() => {
    const fetchQuizes = async () => {
      try {
        const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8080";
        const baseURL = API_URL.startsWith("http") ? API_URL : `https://${API_URL}`;
        const response = await axios.get(`${baseURL}/quizzes`);
        setQuizes(response.data);
      } catch (error) {
        console.error("Error fetching quizes:", error);
      }
    };

    fetchQuizes();
  }, []);

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem("user");
  };

  return (
    <div className="home-container">
      <h1 className="home-title">Quiz Titles</h1>
      <ul className="home-list">
        {quizes.map((quiz, index) => (
          <li className="home-list-items" key={index}>
            <Link to={`/quiz/${quiz.id}`}>{quiz.title}</Link>
          </li>
        ))}
      </ul>
      <div className="wrapp-buttons">
        {user && user.data.user.isTeacher && (
          <>
            <Link to={"/addQuiz"}>
              <button className="Home-button">Create Quiz</button>
            </Link>
            <Link to={"/admin-dashboard"}>
              <button className="Home-button" style={{ marginLeft: "20px", backgroundColor: "#333" }}>Admin Dashboard</button>
            </Link>
          </>
        )}
        {user && (
          <>
            <Link to={"/profile"}>
              <button className="Home-button" style={{ marginRight: "20px", backgroundColor: "#007bff" }}>My Profile</button>
            </Link>
            <button className="Home-button" onClick={handleLogout}>
              Logout
            </button>
          </>
        )}
      </div>
    </div>
  );
};

export default Home;
