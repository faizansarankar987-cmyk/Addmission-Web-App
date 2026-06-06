import React, { useState } from "react";
import axios from "axios";
import api from "../services/api";
import { Link, useNavigate } from "react-router-dom";
const Login = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState({ email: "", password: "" });
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    
    try {
      // 2. Use API.post and provide just the relative endpoint path
      const response = await api.post("/student/login", user);
      
      alert(response.data);
      localStorage.setItem("email", user.email);
      navigate("/dashboard");
    } catch (error) {
      alert(error.response?.data || "Invalid Email or Password");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    /* 1. This wrapper guarantees full-screen alignment and centers the inner box completely */
    <div className="min-h-screen w-full flex items-center justify-center bg-slate-100 p-4 sm:p-6 md:p-8">
      
      {/* 2. Main Box: Handles max-width so it doesn't break, and defines equal split behavior */}
      <div className="bg-white w-full max-w-4xl rounded-2xl shadow-xl border border-slate-200/60 overflow-hidden grid grid-cols-1 md:grid-cols-12 min-h-[550px]">
        
        {/* Left branding panel - Hidden on small mobile frames, matches col width automatically */}
        <div className="hidden md:flex md:col-span-5 bg-gradient-to-br from-blue-700 via-indigo-600 to-violet-600 p-8 flex-col justify-between text-white">
          <div>
            <div className="flex items-center gap-2 mb-8 opacity-90">
              <span className="font-bold tracking-wider text-sm uppercase border-b-2 border-white/40 pb-0.5">EduPortal</span>
            </div>
            <h1 className="text-2xl font-extrabold tracking-tight leading-tight">Admissions Portal</h1>
            <p className="mt-3 text-blue-100/80 text-sm leading-relaxed">
              Log in to process application metrics, track status reviews, and finalize enrollment criteria.
            </p>
          </div>
          <p className="text-xs text-blue-200/60">Official Admissions Office Desk</p>
        </div>

        {/* Right input panel - Full width on mobile, scales on desk */}
        <div className="col-span-1 md:col-span-7 p-6 sm:p-10 flex flex-col justify-center">
          <div className="w-full max-w-sm mx-auto">
            <h2 className="text-xl font-bold text-slate-800">Student Login</h2>
            <p className="text-slate-500 text-xs mt-1 mb-6">Enter your credential access key.</p>

            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-xs font-medium text-slate-600 mb-1.5">Email Address</label>
                <input
                  type="email"
                  name="email"
                  placeholder="name@university.edu"
                  value={user.email}
                  onChange={handleChange}
                  className="w-full px-3 py-2.5 bg-slate-50 border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 text-sm transition-all"
                  required
                />
              </div>

              <div>
                <div className="flex justify-between mb-1.5">
                  <label className="text-xs font-medium text-slate-600">Password</label>
                  <Link to="/forget" className="text-xs text-blue-600 hover:underline">Forgot?</Link>
                </div>
                <input
                  type="password"
                  name="password"
                  placeholder="••••••••"
                  value={user.password}
                  onChange={handleChange}
                  className="w-full px-3 py-2.5 bg-slate-50 border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 text-sm transition-all"
                  required
                />
              </div>

              <button
                type="submit"
                disabled={isLoading}
                className="w-full bg-blue-600 hover:bg-blue-700 text-white py-2.5 rounded-xl text-sm font-medium transition duration-150 disabled:opacity-50 mt-2"
              >
                {isLoading ? "Verifying..." : "Sign In to Portal"}
              </button>
            </form>

            <p className="text-center text-xs text-slate-500 mt-6 pt-4 border-t border-slate-100">
              New applicant? <Link to="/register" className="text-blue-600 font-semibold hover:underline">Create an account</Link>
            </p>
          </div>
        </div>

      </div>
    </div>
  );
};

export default Login;