import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../services/api";
import { CreditCard, IndianRupee } from "lucide-react";

const Payment = () => {
  const { id } = useParams();

  const [application, setApplication] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadApplication();
  }, []);

  const loadApplication = async () => {
    try {
      const res = await api.get(`/application/getById/${id}`);
      setApplication(res.data);
    } catch (err) {
      console.error(err);
      alert("Application not found");
    } finally {
      setLoading(false);
    }
  };

  const handleStripePayment = async () => {
    try {
      const res = await api.post("/Courses/checkout", {
        coursename: application.coursename,
        fees: application.remainingFees,
        applicationId: application.id,
      });

      if (res.data.sessionUrl) {
        window.location.href = res.data.sessionUrl;
      }
    } catch (err) {
      console.error(err);
      alert("Stripe Payment Failed");
    }
  };

  const handleUpiPayment = async () => {
    try {
      const res = await api.post("/Courses/upi/create-order", {
        applicationId: application.id,
        amount: application.remainingFees,
      });

      alert("Order Created : " + res.data.orderId);
    } catch (err) {
      console.error(err);
      alert("UPI Payment Failed");
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen flex justify-center items-center">
        Loading...
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-slate-100 flex justify-center items-center p-6">
      <div className="bg-white max-w-xl w-full rounded-3xl shadow-xl overflow-hidden">

        <div className="bg-gradient-to-r from-blue-600 to-indigo-600 text-white p-6">
          <h1 className="text-3xl font-bold">
            Fee Payment
          </h1>
          <p>Complete your course fee payment</p>
        </div>

        <div className="p-6 space-y-4">

          <div className="bg-slate-100 p-4 rounded-xl">
            <p className="text-gray-500">Application ID</p>
            <h2 className="font-bold text-xl">
              #{application.id}
            </h2>
          </div>

          <div className="bg-slate-100 p-4 rounded-xl">
            <p className="text-gray-500">Course</p>
            <h2 className="font-bold text-xl">
              {application.coursename}
            </h2>
          </div>

          <div className="grid grid-cols-2 gap-4">

            <div className="bg-green-50 border border-green-200 p-4 rounded-xl">
              <p className="text-green-700">Paid</p>
              <h3 className="text-xl font-bold text-green-600">
                ₹ {application.amountPaid}
              </h3>
            </div>

            <div className="bg-red-50 border border-red-200 p-4 rounded-xl">
              <p className="text-red-700">Remaining</p>
              <h3 className="text-xl font-bold text-red-600">
                ₹ {application.remainingFees}
              </h3>
            </div>

          </div>

          <div className="bg-blue-50 border border-blue-200 p-4 rounded-xl">
            <p>Total Fees</p>
            <h2 className="text-2xl font-bold">
              ₹ {application.totalFees}
            </h2>
          </div>

          <button
            onClick={handleStripePayment}
            className="w-full bg-blue-600 hover:bg-blue-700 text-white py-3 rounded-xl flex items-center justify-center gap-2"
          >
            <CreditCard size={20} />
            Pay With Stripe
          </button>

          <button
            onClick={handleUpiPayment}
            className="w-full bg-green-600 hover:bg-green-700 text-white py-3 rounded-xl"
          >
            Pay With UPI
          </button>

        </div>
      </div>
    </div>
  );
};

export default Payment;