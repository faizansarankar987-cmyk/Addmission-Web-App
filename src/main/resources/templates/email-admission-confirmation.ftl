<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Admission Confirmation</title>
  <style type="text/css">
    /* General resets */
    body, table, td, a { -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; }
    table { border-collapse: collapse !important; }
    body { margin: 0 !important; padding: 0 !important; width: 100% !important; background-color: #f4f4f4; }

    /* Container */
    .email-container {
      max-width: 600px;
      margin: 0 auto;
      background-color: #ffffff;
      border-radius: 8px;
      overflow: hidden;
      box-shadow: 0 2px 8px rgba(0,0,0,0.1);
      font-family: Arial, sans-serif;
    }
    .header {
      background-color: #2c3e50;
      color: #ffffff;
      padding: 20px;
      text-align: center;
    }
    .header h1 {
      margin: 0;
      font-size: 24px;
    }
    .body-section {
      padding: 20px;
      color: #333333;
      line-height: 1.5;
    }
    .body-section p {
      margin: 0 0 15px;
    }
    .info-table {
      width: 100%;
      margin-top: 20px;
      border: 1px solid #dddddd;
    }
    .info-table th, .info-table td {
      padding: 12px;
      border: 1px solid #dddddd;
      text-align: left;
      font-size: 14px;
    }
    .info-table th {
      background-color: #f9f9f9;
    }
    .btn {
      display: inline-block;
      padding: 10px 20px;
      background-color: #27ae60;
      color: #ffffff !important;
      text-decoration: none;
      border-radius: 4px;
      margin-top: 20px;
    }
    .footer {
      background-color: #ecf0f1;
      padding: 15px;
      text-align: center;
      font-size: 12px;
      color: #777777;
    }
    .footer a {
      color: #2980b9;
      text-decoration: none;
    }
  </style>
</head>
<body>
  <table width="100%" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td align="center">
        <div class="email-container">
          <!-- Header -->
          <div class="header">
            <h1>🎓 Admission Confirmed</h1>
          </div>

          <!-- Body -->
          <div class="body-section">
            <p>Dear <strong>${studentname!}</strong>,</p>
            <p>We are delighted to inform you that your application has been accepted and your admission to the course <strong>${coursename!}</strong> has been confirmed.</p>

            <p>Below are the details of your admission:</p>
            <table class="info-table" cellpadding="0" cellspacing="0">
              <tr>
                <th>Application ID</th>
                <td>${applicationId!}</td>
              </tr>
              <tr>
                <th>Course Name</th>
                <td>${coursename!}</td>
              </tr>
              <tr>
                <th>Date of Confirmation</th>
                <td>${date!}</td>
              </tr>
              <tr>
                <th>Contact Email</th>
                <td>${contactEmail!}</td>
              </tr>
            </table>

            <p>If you’d like to check or update your application, please click the button below:</p>
            <a href="${portalLink!}" class="btn">Go to Student Portal</a>

            <p>Once again, congratulations and welcome aboard! If you have any questions, feel free to reach out.</p>
          </div>

          <!-- Footer -->
          <div class="footer">
            &copy; ${year!} Your Institute Name. All rights reserved.<br/>
            <a href="mailto:${contactEmail!}">Contact Us</a>
          </div>
        </div>
      </td>
    </tr>
  </table>
</body>
</html>
