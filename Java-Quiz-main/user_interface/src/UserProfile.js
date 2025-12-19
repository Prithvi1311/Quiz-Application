import React, { useEffect, useState, useContext } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { UserContext } from "./userContext";
import "./style/Home.css"; // Reusing Home styles for consistency

const UserProfile = () => {
    const { user } = useContext(UserContext);
    const [submissions, setSubmissions] = useState([]);
    const username = user?.data?.user?.username;

    useEffect(() => {
        const fetchSubmissions = async () => {
            if (username) {
                try {
                    const response = await axios.get(`${process.env.REACT_APP_API_URL || "http://localhost:8080"}/submissions/user/${username}`);
                    setSubmissions(response.data);
                } catch (error) {
                    console.error("Error fetching submissions:", error);
                }
            }
        };

        fetchSubmissions();
    }, [username]);

    return (
        <div className="home-container">
            <h1 className="home-title">My Quiz History</h1>
            <div style={{ maxWidth: "800px", margin: "0 auto", backgroundColor: "#fff", padding: "20px", borderRadius: "5px" }}>
                <div style={{ marginBottom: "20px", fontSize: "18px", color: "#333" }}>
                    <strong>Username:</strong> {username}
                </div>
                <table style={{ width: "100%", borderCollapse: "collapse" }}>
                    <thead>
                        <tr style={{ borderBottom: "2px solid #ddd", textAlign: "left" }}>
                            <th style={{ padding: "10px" }}>Quiz Title</th>
                            <th style={{ padding: "10px" }}>Score</th>
                            <th style={{ padding: "10px" }}>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        {submissions.map((sub) => (
                            <tr key={sub.id} style={{ borderBottom: "1px solid #eee" }}>
                                <td style={{ padding: "10px" }}>{sub.quizTitle}</td>
                                <td style={{ padding: "10px" }}>{sub.score} / {sub.totalQuestions}</td>
                                <td style={{ padding: "10px" }}>{new Date(sub.submissionTime).toLocaleString()}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                {submissions.length === 0 && <p style={{ textAlign: "center", marginTop: "20px" }}>No quiz attempts yet.</p>}
            </div>
            <div className="wrapp-buttons">
                <Link to="/home">
                    <button className="Home-button">Back to Home</button>
                </Link>
            </div>
        </div>
    );
};

export default UserProfile;
