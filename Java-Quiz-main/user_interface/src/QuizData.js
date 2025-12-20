import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./style/QuizData.css";
import { FiChevronLeft } from "react-icons/fi";

const QuizData = () => {
  const navigate = useNavigate();

  const [quizDetails, setQuizDetails] = useState({});
  const [selectedOptions, setSelectedOptions] = useState({});
  const { id } = useParams();

  useEffect(() => {
    const fetchQuizDetails = async () => {
      try {
        const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8080";
        const baseURL = API_URL.startsWith("http") ? API_URL : `https://${API_URL}`;
        const response = await axios.get(`${baseURL}/quizzes/${id}`);
        setQuizDetails(response.data);
        console.log(response.data);
      } catch (error) {
        console.error("Error fetching quiz details:", error);
      }
    };

    fetchQuizDetails();
  }, [id]);

  const handleOptionClick = (questionId, selectedOption, correctOption) => {
    setSelectedOptions(prev => ({
      ...prev,
      [questionId]: selectedOption
    }));

    if (selectedOption === correctOption) {
      toast.success("Correct choice!");
    } else {
      toast.error("Incorrect choice. Try again!");
    }
  };

  const getOptionClass = (questionId, option, correctOption) => {
    const selected = selectedOptions[questionId];
    if (!selected) return "option";

    if (option === correctOption) {
      return "option correct";
    }
    if (option === selected && selected !== correctOption) {
      return "option incorrect";
    }
    return "option";
  };

  const [scoreResult, setScoreResult] = useState(null);

  const handleSubmit = async () => {
    let score = 0;
    const totalQuestions = quizDetails.questions?.length || 0;

    quizDetails.questions.forEach(q => {
      if (selectedOptions[q.id] === q.correctOption) {
        score++;
      }
    });

    const submission = {
      quizId: quizDetails.id,
      quizTitle: quizDetails.title,
      username: JSON.parse(localStorage.getItem("user")).data.user.username,
      score: score,
      totalQuestions: totalQuestions
    };

    try {
      const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8080";
      const baseURL = API_URL.startsWith("http") ? API_URL : `https://${API_URL}`;
      await axios.post(`${baseURL}/submissions`, submission);
      setScoreResult({ score, totalQuestions });
      toast.success("Quiz Submitted Successfully!");
    } catch (error) {
      console.error("Error submitting quiz:", error);
      toast.error("Failed to submit quiz.");
    }
  };

  if (scoreResult) {
    return (
      <div className="quiz-details" style={{ textAlign: "center", marginTop: "50px" }}>
        <div className="header">
          <button onClick={() => navigate("/home")} className="back-button">
            <FiChevronLeft />
          </button>
          <h1 className="quiz-title">Quiz Result</h1>
        </div>
        <div style={{
          backgroundColor: "#fff",
          padding: "40px",
          borderRadius: "10px",
          boxShadow: "0 4px 8px rgba(0,0,0,0.1)",
          marginTop: "20px"
        }}>
          <h2 style={{ color: "#4d47c3", fontSize: "32px" }}>Your Score</h2>
          <h1 style={{ fontSize: "64px", margin: "20px 0", color: "#333" }}>
            {scoreResult.score} / {scoreResult.totalQuestions}
          </h1>
          <p style={{ fontSize: "18px", color: "#666" }}>
            {scoreResult.score === scoreResult.totalQuestions ? "Perfect Score! 🎉" :
              scoreResult.score >= scoreResult.totalQuestions / 2 ? "Good Job! 👍" : "Keep Practicing! 💪"}
          </p>
          <div style={{ marginTop: "40px" }}>
            <button
              onClick={() => navigate("/home")}
              style={{
                backgroundColor: "#4d47c3",
                color: "white",
                padding: "15px 30px",
                fontSize: "18px",
                border: "none",
                borderRadius: "5px",
                cursor: "pointer",
                marginRight: "10px"
              }}
            >
              Back to Home
            </button>
            <button
              onClick={() => { setScoreResult(null); setSelectedOptions({}); }}
              style={{
                backgroundColor: "#ccc",
                color: "#333",
                padding: "15px 30px",
                fontSize: "18px",
                border: "none",
                borderRadius: "5px",
                cursor: "pointer"
              }}
            >
              Retake Quiz
            </button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <>
      <div className="quiz-details">
        <div className="header">
          <button onClick={() => navigate("/home")} className="back-button">
            <FiChevronLeft />
          </button>
          <h1 className="quiz-title">Quiz Details</h1>
        </div>
        {quizDetails.questions?.map((i, questionIndex) => (
          <div key={i.id} className="question-container">
            <p className="question-text">{i.text}</p>
            <ul className="options-list">
              {i.options?.map((option, index) => (
                <li
                  key={index}
                  className={getOptionClass(i.id, option, i.correctOption)}
                  onClick={() => handleOptionClick(i.id, option, i.correctOption)}
                  style={{ pointerEvents: selectedOptions[i.id] ? 'none' : 'auto' }}
                >
                  {option}
                </li>
              ))}
            </ul>
          </div>
        ))}

        <div style={{ display: "flex", justifyContent: "center", marginTop: "20px", marginBottom: "40px" }}>
          <button
            onClick={handleSubmit}
            style={{
              backgroundColor: "#4d47c3",
              color: "white",
              padding: "15px 30px",
              fontSize: "18px",
              border: "none",
              borderRadius: "5px",
              cursor: "pointer"
            }}
          >
            Submit Quiz
          </button>
        </div>

      </div>
    </>
  );
};

export default QuizData;
