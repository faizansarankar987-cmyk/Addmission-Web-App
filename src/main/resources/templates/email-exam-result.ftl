<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
body {
  font-family: 'Poppins', Arial, sans-serif;
  background: #f6f9ff;
  color: #333;
}
.container {
  background: #fff;
  padding: 25px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
  max-width: 650px;
  margin: auto;
}
h2 {
  color: #2c4fff;
}
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 15px;
}
th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}
th {
  background: #eef3ff;
}
.correct {
  color: green;
  font-weight: bold;
}
.wrong {
  color: red;
  font-weight: bold;
}
.footer {
  margin-top: 25px;
  font-size: 13px;
  color: #777;
}
</style>
</head>
<body>
<div class="container">
  <h2>ISEES Online Exam Result</h2>

  <p>Hello <b>${studentName}</b>,</p>
  <p>Thank you for completing your online exam. Below are your results:</p>

  <p><b>Score:</b> ${score}/${total}</p>

  <table>
    <thead>
      <tr>
        <th>Question</th>
        <th>Your Answer</th>
        <th>Correct Answer</th>
      </tr>
    </thead>
    <tbody>
      <#list answers as a>
      <tr>
        <td>${a.question}</td>
        <td class="${a.correct?string('correct','wrong')}">${a.selected}</td>
        <td>${a.correctAnswer}</td>
      </tr>
      </#list>
    </tbody>
  </table>

  <div class="footer">
    © ${year} ISEES Technologies — This is an automated email. Please do not reply.
  </div>
</div>
</body>
</html>
