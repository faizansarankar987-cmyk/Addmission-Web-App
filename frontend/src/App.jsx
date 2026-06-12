import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import ForgotPassword from "./pages/forgotpassword";
import ResetPassword from "./pages/resetpassword";
import Dashboard from "./pages/dashboaord";
import Payment  from "./pages/payment";
// import course from "./pages"

// import Dashboard from "./pages/Dashboard";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />

        <Route
          path="/register"
          element={<Register />}
        />

        <Route path="/forget" element={<ForgotPassword/>}/>
        <Route path="/reset-password" element={<ResetPassword/>}/>
        <Route path="/dashboard" element={<Dashboard/>}/>
        {/* <Route path="/course" element={<Courses/>} /> */}
        <Route path="/payment/:id" element={<Payment />} />
        {/* <Route path="/receipt/:id" element={<Receipt />} /> */}
        {/* <Route path="/certificate/:id" element={<Certificate />} /> */}
        {/* <Route path="/assignments" element={<Assignments />} /> */}
        {/* <Route path="/exams" element={<Exams />} /> */}
        
{/* 
        <Route
          path="/dashboard"
          element={<Dashboard />}
        /> */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;