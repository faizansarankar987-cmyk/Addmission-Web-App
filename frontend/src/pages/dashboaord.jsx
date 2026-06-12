import React, { useEffect, useState, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

import {
  GraduationCap,
  CreditCard,
  CheckCircle2,
  Clock,
  FolderOpen,
  IndianRupee,
  BookOpen,
  FileText,
  Award,
  ClipboardList,
  LogOut,
  User,
} from "lucide-react";

const Dashboard = () => {
  const navigate = useNavigate();

  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  const email = localStorage.getItem("email");

  const fetchDashboard = useCallback(async () => {
    try {
      const response = await api.get(`/dashboard/${email}`);
      setData(response.data);
    } catch (error) {
      console.error("Dashboard Error:", error);
    } finally {
      setLoading(false);
    }
  }, [email]);

  useEffect(() => {
    fetchDashboard();
  }, [fetchDashboard]);

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat("en-IN", {
      style: "currency",
      currency: "INR",
      maximumFractionDigits: 0,
    }).format(amount || 0);
  };

  const totalApplications = data?.applications?.length || 0;

  const approvedApplications =
    data?.applications?.filter(
      (app) => app.applicationStatus === "APPROVED"
    ).length || 0;

  const totalPaid =
    data?.applications?.reduce(
      (sum, app) => sum + (app.amountPaid || 0),
      0
    ) || 0;

  const totalRemaining =
    data?.applications?.reduce(
      (sum, app) => sum + (app.remainingFees || 0),
      0
    ) || 0;

  const totalFees =
    data?.applications?.reduce(
      (sum, app) => sum + (app.totalFees || 0),
      0
    ) || 0;

  const progress =
    totalFees > 0
      ? Math.round((totalPaid / totalFees) * 100)
      : 0;

  const ApplicationStatusBadge = ({ status }) => {
    const approved = status === "APPROVED";

    return (
      <span
        className={`px-3 py-1 rounded-full text-xs font-semibold flex items-center gap-1 ${
          approved
            ? "bg-green-100 text-green-700"
            : "bg-yellow-100 text-yellow-700"
        }`}
      >
        {approved ? (
          <CheckCircle2 size={14} />
        ) : (
          <Clock size={14} />
        )}
        {status}
      </span>
    );
  };

  const PaymentStatusBadge = ({ status }) => {
    const colors = {
      PAID: "bg-green-100 text-green-700",
      PARTIAL: "bg-orange-100 text-orange-700",
      UNPAID: "bg-red-100 text-red-700",
    };

    return (
      <span
        className={`px-3 py-1 rounded-full text-xs font-semibold ${
          colors[status] || "bg-gray-100 text-gray-700"
        }`}
      >
        {status}
      </span>
    );
  };

  if (loading) {
    return (
      <div className="min-h-screen flex justify-center items-center bg-slate-100">
        <div className="w-12 h-12 border-4 border-blue-600 border-t-transparent rounded-full animate-spin"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-slate-100 p-4 md:p-8">
      <div className="max-w-7xl mx-auto space-y-6">

        {/* Header */}
        <div className="bg-gradient-to-r from-blue-600 via-indigo-600 to-purple-600 rounded-3xl p-8 text-white shadow-xl">
          <div className="flex flex-col md:flex-row justify-between gap-5">

            <div>
              <h1 className="text-4xl font-bold">
                Welcome Back 👋
              </h1>

              <p className="text-xl mt-2">
                {data?.studentname}
              </p>

              <p className="text-blue-100 mt-1">
                Student Admission Dashboard
              </p>
            </div>

            <div className="flex flex-col gap-3">

              <div className="bg-white/20 backdrop-blur-lg px-4 py-3 rounded-xl">
                {email}
              </div>

              <button
                onClick={logout}
                className="bg-red-500 hover:bg-red-600 px-4 py-2 rounded-xl flex items-center gap-2 justify-center"
              >
                <LogOut size={18} />
                Logout
              </button>

            </div>
          </div>
        </div>

        {/* Student Profile */}
        <div className="bg-white rounded-2xl p-6 shadow-md">
          <div className="flex items-center gap-4">
            <div className="bg-blue-100 p-4 rounded-full">
              <User className="text-blue-600" size={35} />
            </div>

            <div>
              <h2 className="text-xl font-bold">
                {data?.studentname}
              </h2>
              <p className="text-gray-500">{email}</p>
            </div>
          </div>
        </div>

        {/* Statistics */}
        <div className="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-5">

          <div className="bg-white rounded-2xl p-6 shadow-md">
            <div className="flex justify-between">
              <p>Applications</p>
              <GraduationCap className="text-blue-600" />
            </div>

            <h2 className="text-3xl font-bold text-blue-600 mt-4">
              {totalApplications}
            </h2>
          </div>

          <div className="bg-white rounded-2xl p-6 shadow-md">
            <div className="flex justify-between">
              <p>Approved</p>
              <CheckCircle2 className="text-green-600" />
            </div>

            <h2 className="text-3xl font-bold text-green-600 mt-4">
              {approvedApplications}
            </h2>
          </div>

          <div className="bg-white rounded-2xl p-6 shadow-md">
            <div className="flex justify-between">
              <p>Total Paid</p>
              <IndianRupee className="text-emerald-600" />
            </div>

            <h2 className="text-xl font-bold text-emerald-600 mt-4">
              {formatCurrency(totalPaid)}
            </h2>
          </div>

          <div className="bg-white rounded-2xl p-6 shadow-md">
            <div className="flex justify-between">
              <p>Remaining Fees</p>
              <CreditCard className="text-red-600" />
            </div>

            <h2 className="text-xl font-bold text-red-600 mt-4">
              {formatCurrency(totalRemaining)}
            </h2>
          </div>

        </div>

        {/* Progress */}
        <div className="bg-white rounded-2xl p-6 shadow-md">
          <div className="flex justify-between mb-3">
            <h2 className="font-bold text-lg">
              Fee Progress
            </h2>

            <span className="font-bold text-blue-600">
              {progress}%
            </span>
          </div>

          <div className="w-full h-4 bg-gray-200 rounded-full">
            <div
              className="h-4 rounded-full bg-gradient-to-r from-blue-500 to-purple-600"
              style={{ width: `${progress}%` }}
            />
          </div>
        </div>

        {/* Quick Actions */}
        <div className="bg-white rounded-2xl p-6 shadow-md">
          <h2 className="text-xl font-bold mb-5">
            Quick Actions
          </h2>

          <div className="grid md:grid-cols-5 gap-4">

            <button
              onClick={() => navigate("/course")}
              className="bg-blue-600 text-white p-4 rounded-xl hover:bg-blue-700"
            >
              <BookOpen className="mx-auto mb-2" />
              Apply Course
            </button>

            <button
              onClick={() => navigate("/assignments")}
              className="bg-green-600 text-white p-4 rounded-xl hover:bg-green-700"
            >
              <FileText className="mx-auto mb-2" />
              Assignments
            </button>

            <button
              onClick={() => navigate("/exams")}
              className="bg-orange-600 text-white p-4 rounded-xl hover:bg-orange-700"
            >
              <ClipboardList className="mx-auto mb-2" />
              Exams
            </button>

            <button
              onClick={() => navigate("/certificate")}
              className="bg-purple-600 text-white p-4 rounded-xl hover:bg-purple-700"
            >
              <Award className="mx-auto mb-2" />
              Certificates
            </button>

            <button
              onClick={() => navigate("/receipt")}
              className="bg-pink-600 text-white p-4 rounded-xl hover:bg-pink-700"
            >
              <FileText className="mx-auto mb-2" />
              Receipts
            </button>

          </div>
        </div>

        {/* Applications */}
        <div>
          <h2 className="text-2xl font-bold mb-4">
            My Applications
          </h2>

          {!data?.applications?.length ? (
            <div className="bg-white rounded-2xl p-10 text-center shadow-md">
              <FolderOpen
                size={60}
                className="mx-auto text-gray-400"
              />
              <p className="mt-4 text-gray-500">
                No Applications Found
              </p>
            </div>
          ) : (
            <div className="grid md:grid-cols-2 xl:grid-cols-3 gap-6">

              {data.applications.map((app) => (
                <div
                  key={app.id}
                  className="bg-white rounded-2xl p-6 shadow-md hover:shadow-xl transition"
                >
                  <div className="flex justify-between">
                    <div>
                      <h3 className="font-bold text-lg">
                        {app.coursename}
                      </h3>

                      <p className="text-sm text-gray-500">
                        Application #{app.id}
                      </p>
                    </div>

                    <ApplicationStatusBadge
                      status={app.applicationStatus}
                    />
                  </div>

                  <div className="mt-5 space-y-3">

                    <div className="flex justify-between">
                      <span>Total Fee</span>
                      <span>
                        {formatCurrency(app.totalFees)}
                      </span>
                    </div>

                    <div className="flex justify-between">
                      <span>Paid</span>
                      <span className="text-green-600 font-semibold">
                        {formatCurrency(app.amountPaid)}
                      </span>
                    </div>

                    <div className="flex justify-between">
                      <span>Remaining</span>
                      <span className="text-red-600 font-semibold">
                        {formatCurrency(app.remainingFees)}
                      </span>
                    </div>

                  </div>

                  <div className="mt-4">
                    <PaymentStatusBadge
                      status={app.paymentStatus}
                    />
                  </div>

                  <div className="grid grid-cols-3 gap-2 mt-5">

                    {app.remainingFees > 0 && (
                      <button
                        onClick={() =>
                          navigate(`/payment/${app.id}`)
                        }
                        className="bg-blue-600 hover:bg-blue-700 text-white py-2 rounded-lg text-sm"
                      >
                        Pay Fee
                      </button>
                    )}

                    {app.amountPaid > 0 && (
                      <button
                        onClick={() =>
                          navigate(`/receipt/${app.id}`)
                        }
                        className="bg-green-600 hover:bg-green-700 text-white py-2 rounded-lg text-sm"
                      >
                        Receipt
                      </button>
                    )}

                    {app.paymentStatus === "PAID" && (
                      <button
                        onClick={() =>
                          navigate(`/certificate/${app.id}`)
                        }
                        className="bg-purple-600 hover:bg-purple-700 text-white py-2 rounded-lg text-sm"
                      >
                        Certificate
                      </button>
                    )}

                  </div>
                </div>
              ))}

            </div>
          )}
        </div>

      </div>
    </div>
  );
};

export default Dashboard;