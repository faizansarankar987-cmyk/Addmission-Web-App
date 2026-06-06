import React, { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

const ForgotPassword = () => {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {

            const response = await api.post("/student/forget", {
                email: email
            });

            alert(response.data);

            localStorage.setItem("resetEmail", email);

            navigate("/reset-password");

        } catch (error) {

            alert(
                error.response?.data ||
                "Failed to send OTP"
            );
        }
    };

    return (
        <div className="min-h-screen flex justify-center items-center bg-slate-100">
            <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-md">
                <h2 className="text-2xl font-bold mb-6 text-center">
                    Forgot Password
                </h2>

                <form onSubmit={handleSubmit}>
                    <input
                        type="email"
                        placeholder="Enter Email"
                        className="w-full border p-3 rounded mb-4"
                        value={email}
                        onChange={(e) =>
                            setEmail(e.target.value)
                        }
                        required
                    />

                    <button
                        type="submit"
                        className="w-full bg-blue-600 text-white p-3 rounded"
                    >
                        Send OTP
                    </button>
                </form>
            </div>
        </div>
    );
};

export default ForgotPassword;