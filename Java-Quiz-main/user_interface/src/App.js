import React, { useContext } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import Login from "./Login";
import Register from "./Register";
import Home from "./Home";
import QuizData from "./QuizData";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import AddQuiz from "./AddQuiz";
import AdminDashboard from "./AdminDashboard";
import UserProfile from "./UserProfile";
import { UserContext } from "./userContext";

const App = () => {
  const { user } = useContext(UserContext);

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={user ? <Home /> : <Navigate to="/" />} />
        <Route
          path="/quiz/:id"
          element={user ? <QuizData /> : <Navigate to="/" />}
        />
        <Route
          path="/addQuiz"
          element={user ? <AddQuiz /> : <Navigate to="/" />}
        />
        <Route
          path="/admin-dashboard"
          element={user ? <AdminDashboard /> : <Navigate to="/" />}
        />
        <Route
          path="/profile"
          element={user ? <UserProfile /> : <Navigate to="/" />}
        />
      </Routes>
      <ToastContainer autoClose={2000} limit={3} />
    </Router>
  );
};

export default App;
