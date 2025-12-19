import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "./style/Home.css"; // Reusing Home styles for consistency

const AdminDashboard = () => {
    const [submissions, setSubmissions] = useState([]);

    useEffect(() => {
        const fetchSubmissions = async () => {
            try {
                const response = await axios.get(`${process.env.REACT_APP_API_URL || "http://localhost:8080"}/submissions`);
                setSubmissions(response.data);
            } catch (error) {
                console.error("Error fetching submissions:", error);
            }
        };

        fetchSubmissions();
    }, []);

    return (
        <div className="home-container">
            <h1 className="home-title">Admin Dashboard - Student Scores</h1>
            <div style={{ maxWidth: "800px", margin: "0 auto", backgroundColor: "#fff", padding: "20px", borderRadius: "5px" }}>
                <table style={{ width: "100%", borderCollapse: "collapse" }}>
                    <thead>
                        <tr style={{ borderBottom: "2px solid #ddd", textAlign: "left" }}>
                            <th style={{ padding: "10px" }}>Student</th>
                            <th style={{ padding: "10px" }}>Quiz Title</th>
                            <th style={{ padding: "10px" }}>Score</th>
                            <th style={{ padding: "10px" }}>Time</th>
                        </tr>
                    </thead>
                    <tbody>
                        {submissions.map((sub) => (
                            <tr key={sub.id} style={{ borderBottom: "1px solid #eee" }}>
                                <td style={{ padding: "10px" }}>{sub.username}</td>
                                <td style={{ padding: "10px" }}>{sub.quizTitle}</td>
                                <td style={{ padding: "10px" }}>{sub.score} / {sub.totalQuestions}</td>
                                <td style={{ padding: "10px" }}>{new Date(sub.submissionTime).toLocaleString()}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                {submissions.length === 0 && <p style={{ textAlign: "center", marginTop: "20px" }}>No submissions yet.</p>}
            </div>
            <div className="wrapp-buttons">
                <Link to="/home">
                    <button className="Home-button">Back to Home</button>
                </Link>
                <Link to="/addQuiz">
                    <button className="Home-button" style={{ marginLeft: "20px" }}>Create Quiz</button>
                </Link>
            </div>
        </div>
    );
};

export default AdminDashboard;
