import React, { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

const ResetPassword = () => {

    const navigate = useNavigate();

    const [form, setForm] = useState({
        email: localStorage.getItem("resetEmail") || "",
        otp: "",
        password: ""
    });

    const handleChange = (e) => {

        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const response = await api.post(
                "/student/reset",
                form
            );

            alert(response.data);

            localStorage.removeItem("resetEmail");

            navigate("/login");

        } catch (error) {

            alert(
                error.response?.data ||
                "Invalid OTP"
            );
        }
    };

    return (
        <div className="min-h-screen flex justify-center items-center bg-slate-100">
            <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-md">

                <h2 className="text-2xl font-bold mb-6 text-center">
                    Reset Password
                </h2>

                <form onSubmit={handleSubmit}>

                    <input
                        type="email"
                        name="email"
                        value={form.email}
                        onChange={handleChange}
                        placeholder="Email"
                        className="w-full border p-3 rounded mb-4"
                        required
                    />

                    <input
                        type="text"
                        name="otp"
                        value={form.otp}
                        onChange={handleChange}
                        placeholder="Enter OTP"
                        className="w-full border p-3 rounded mb-4"
                        required
                    />

                    <input
                        type="password"
                        name="password"
                        value={form.password}
                        onChange={handleChange}
                        placeholder="New Password"
                        className="w-full border p-3 rounded mb-4"
                        required
                    />

                    <button
                        type="submit"
                        className="w-full bg-green-600 text-white p-3 rounded"
                    >
                        Reset Password
                    </button>

                </form>
            </div>
        </div>
    );
};

export default ResetPassword;