import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
// 1. Using your unified API utility instance
import api from "../services/api";

function Register() {
  const navigate = useNavigate();

  // Unified object state to keep your code clean and standardized
  const [user, setUser] = useState({
    name: "",
    email: "",
    password: "",
  });
  
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    setUser({
      ...user,
      [e.target.name]: e.target.value,
    });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setIsLoading(true);

    try {
      // 2. Using the clean relative path via API.post
      const res = await API.post("/student/register", user);

      alert(res.data);
      navigate("/"); // Redirects to login on success
    } catch (error) {
      alert(error.response?.data || "Registration Failed");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    /* Background and Flex alignment to securely center the viewport container */
    <div className="min-h-screen w-full flex items-center justify-center bg-slate-100 p-4 sm:p-6 md:p-8">
      
      {/* Main Structural Layout Card */}
      <div className="bg-white w-full max-w-4xl rounded-2xl shadow-xl border border-slate-200/60 overflow-hidden grid grid-cols-1 md:grid-cols-12 min-h-[550px]">
        
        {/* Left Column: Portal Info Hero Panel (Syncs perfectly with Login look) */}
        <div className="hidden md:flex md:col-span-5 bg-gradient-to-br from-blue-700 via-indigo-600 to-violet-600 p-8 flex-col justify-between text-white">
          <div>
            <div className="flex items-center gap-2 mb-8 opacity-90">
              <span className="font-bold tracking-wider text-sm uppercase border-b-2 border-white/40 pb-0.5">
                EduPortal
              </span>
            </div>
            <h1 className="text-2xl font-extrabold tracking-tight leading-tight">
              Create Applicant Account
            </h1>
            <p className="mt-3 text-blue-100/80 text-sm leading-relaxed">
              Register below to initiate your application profile, request secure identity validation, and access the submission timeline.
            </p>
          </div>
          <p className="text-xs text-blue-200/60">Official Admissions Office Desk</p>
        </div>

        {/* Right Column: Interactive Registration Form Panel */}
        <div className="col-span-1 md:col-span-7 p-6 sm:p-10 flex flex-col justify-center">
          <div className="w-full max-w-sm mx-auto">
            <h2 className="text-xl font-bold text-slate-800">Start Your Application</h2>
            <p className="text-slate-500 text-xs mt-1 mb-6">
              Provide your core profile credentials to set up a student portal key.
            </p>

            <form onSubmit={handleRegister} className="space-y-4">
              {/* Full Name Input Field */}
              <div>
                <label className="block text-xs font-medium text-slate-600 mb-1.5">Full Name</label>
                <input
                  type="text"
                  name="name"
                  placeholder="John Doe"
                  value={user.name}
                  onChange={handleChange}
                  className="w-full px-3 py-2.5 bg-slate-50 border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 text-sm transition-all"
                  required
                />
              </div>

              {/* Email Address Input Field */}
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

              {/* Password Input Field */}
              <div>
                <label className="block text-xs font-medium text-slate-600 mb-1.5">Password</label>
                <input
                  type="password"
                  name="password"
                  placeholder="Create a strong security key"
                  value={user.password}
                  onChange={handleChange}
                  className="w-full px-3 py-2.5 bg-slate-50 border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 text-sm transition-all"
                  required
                />
              </div>

              {/* Submit Button with Dynamic Request Spinner Hook */}
              <button
                type="submit"
                disabled={isLoading}
                className="w-full bg-blue-600 hover:bg-blue-700 text-white py-2.5 rounded-xl text-sm font-medium transition duration-150 disabled:opacity-50 mt-4 shadow-md"
              >
                {isLoading ? "Generating Account..." : "Create Account & Register"}
              </button>
            </form>

            {/* Navigation Redirect Link back to Login page */}
            <p className="text-center text-xs text-slate-500 mt-6 pt-4 border-t border-slate-100">
              Already have an account?{" "}
              <Link to="/" className="text-blue-600 font-semibold hover:underline">
                Login here
              </Link>
            </p>
          </div>
        </div>

      </div>
    </div>
  );
}

export default Register;