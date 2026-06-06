import React, { useEffect, useState, useCallback, useMemo } from "react";
import api from "../services/api";
import { 
  User, 
  GraduationCap, 
  Clock, 
  CheckCircle2, 
  AlertCircle, 
  CreditCard, 
  FolderOpen 
} from "lucide-react";

const Dashboard = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  const email = localStorage.getItem("email");

  const fetchDashboard = useCallback(async () => {
    if (!email) {
      console.error("No email found in localStorage");
      setLoading(false);
      return;
    }

    try {
      const response = await api.get(`/dashboard/${email}`);
      setData(response.data);
    } catch (error) {
      console.error(error);
      alert("Failed to load dashboard. Please try again later.");
    } finally {
      setLoading(false);
    }
  }, [email]);

  useEffect(() => {
    fetchDashboard();
  }, [fetchDashboard]);

  // Format currency cleanly helper
  const formatCurrency = useMemo(() => (amount) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      maximumFractionDigits: 0
    }).format(amount ?? 0);
  }, []);

  // Status Badge Helper Components
  const ApplicationStatusBadge = ({ status }) => {
    const styles = status === "APPROVED" 
      ? "bg-emerald-50 text-emerald-700 border-emerald-200" 
      : "bg-amber-50 text-amber-700 border-amber-200";
    
    return (
      <span className={`inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-xs font-semibold border ${styles}`}>
        {status === "APPROVED" ? <CheckCircle2 className="w-3.5 h-3.5" /> : <Clock className="w-3.5 h-3.5" />}
        {status}
      </span>
    );
  };

  const PaymentStatusBadge = ({ status }) => {
    const styles = {
      PAID: "bg-emerald-50 text-emerald-700 border-emerald-200",
      PARTIAL: "bg-orange-50 text-orange-700 border-orange-200",
      UNPAID: "bg-rose-50 text-rose-700 border-rose-200",
    }[status] || "bg-slate-50 text-slate-700 border-slate-200";

    return (
      <span className={`inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-xs font-semibold border ${styles}`}>
        <CreditCard className="w-3.5 h-3.5" />
        {status}
      </span>
    );
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-slate-50 flex flex-col justify-center items-center gap-3">
        <div className="w-10 h-10 border-4 border-blue-600 border-t-transparent rounded-full animate-spin"></div>
        <h2 className="text-slate-600 font-medium">Loading your dashboard...</h2>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-slate-50 p-4 md:p-8 text-slate-800 antialiased">
      <div className="max-w-7xl mx-auto space-y-6">
        
        {/* Welcome Header Widget */}
        <div className="bg-white border border-slate-200/80 rounded-2xl p-6 shadow-sm flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <div className="flex items-center gap-4">
            <div className="p-3 bg-blue-50 text-blue-600 rounded-xl">
              <User className="w-8 h-8" />
            </div>
            <div>
              <h1 className="text-2xl md:text-3xl font-bold tracking-tight text-slate-900">
                Student Dashboard
              </h1>
              <p className="text-slate-500 mt-0.5 flex items-center gap-1">
                Welcome back, 
                <span className="font-semibold text-blue-600">
                  {data?.studentname || "Student"}
                </span>
              </p>
            </div>
          </div>
          <div className="text-xs bg-slate-100 text-slate-600 px-3 py-1.5 rounded-lg self-start sm:self-center font-mono">
            {email}
          </div>
        </div>

        {/* Applications Container */}
        <div className="bg-white border border-slate-200/80 rounded-2xl shadow-sm overflow-hidden">
          <div className="p-6 border-b border-slate-100 flex items-center gap-2">
            <GraduationCap className="w-5 h-5 text-slate-400" />
            <h2 className="text-lg font-bold text-slate-900">
              My Applications
            </h2>
          </div>

          {!data?.applications || data.applications.length === 0 ? (
            <div className="text-center py-16 px-4">
              <div className="mx-auto w-12 h-12 bg-slate-100 rounded-full flex items-center justify-center text-slate-400 mb-3">
                <FolderOpen className="w-6 h-6" />
              </div>
              <h3 className="text-slate-900 font-semibold mb-1">No applications found</h3>
              <p className="text-slate-500 text-sm max-w-sm mx-auto">
                You haven't submitted any course applications yet. Once you apply, they'll appear here.
              </p>
            </div>
          ) : (
            <div className="overflow-x-auto">
              <table className="w-full text-sm text-left">
                <thead>
                  <tr className="bg-slate-50/70 border-b border-slate-200 text-slate-500 font-semibold uppercase tracking-wider text-xs">
                    <th className="p-4 w-16">ID</th>
                    <th className="p-4">Course</th>
                    <th className="p-4">Application Status</th>
                    <th className="p-4">Payment Status</th>
                    <th className="p-4">Total Fees</th>
                    <th className="p-4">Paid</th>
                    <th className="p-4">Remaining</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-slate-100">
                  {data.applications.map((app) => (
                    <tr key={app.id} className="hover:bg-slate-50/50 transition-colors">
                      <td className="p-4 font-mono text-slate-400 font-medium">#{app.id}</td>
                      <td className="p-4 font-semibold text-slate-900">{app.coursename}</td>
                      <td className="p-4"><ApplicationStatusBadge status={app.applicationStatus} /></td>
                      <td className="p-4"><PaymentStatusBadge status={app.paymentStatus} /></td>
                      <td className="p-4 font-medium text-slate-600">{formatCurrency(app.totalFees)}</td>
                      <td className="p-4 font-semibold text-emerald-600">{formatCurrency(app.amountPaid)}</td>
                      <td className="p-4">
                        <span className={`font-semibold ${app.remainingFees > 0 ? 'text-rose-600' : 'text-slate-500'}`}>
                          {formatCurrency(app.remainingFees)}
                        </span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>

      </div>
    </div>
  );
};

export default Dashboard;