
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { apiRequest } from "../api";

export default function Register() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    iin: "",
    firstName: "",
    lastName: "",
    birthDate: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(false);

  function updateField(event) {
    setForm({ ...form, [event.target.name]: event.target.value });
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setError("");
    setSuccess("");

    if (Object.values(form).some((value) => !value.trim())) {
      setError("Заполните все поля.");
      return;
    }

    if (form.password.length < 8) {
      setError("Пароль должен содержать минимум 8 символов.");
      return;
    }

    if (form.password !== form.confirmPassword) {
      setError("Пароли не совпадают.");
      return;
    }

    setLoading(true);

    try {
      const { confirmPassword, ...request } = form;

      await apiRequest("/auth/register", {
        method: "POST",
        body: JSON.stringify(request),
      });

      setSuccess("Регистрация успешна! Теперь войдите в аккаунт.");
      setTimeout(() => navigate("/login"), 1200);
    } catch (err) {
      setError(err.message || "Не удалось зарегистрироваться.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <main className="auth-page">
      <section className="auth-card register-card">
        <div className="brand"><span>N</span> N-Bank</div>
        <p className="eyebrow">GET STARTED</p>
        <h1>Create account</h1>
        <p className="muted">Создайте свой банковский аккаунт.</p>

        <form onSubmit={handleSubmit}>
          <label htmlFor="firstName">Name</label>
          <input
            id="firstName"
            name="firstName"
            value={form.firstName}
            onChange={updateField}
            placeholder="Имя"
            required
          />

          <label htmlFor="lastName">Last name</label>
          <input
            id="lastName"
            name="lastName"
            value={form.lastName}
            onChange={updateField}
            placeholder="Фамилия"
            required
          />

          <label htmlFor="iin">IIN</label>
          <input
            id="iin"
            name="iin"
            value={form.iin}
            onChange={updateField}
            placeholder="12 цифр"
            minLength={12}
            maxLength={12}
            required
          />

          <label htmlFor="birthDate">Date of birth</label>
          <input
            id="birthDate"
            name="birthDate"
            type="date"
            value={form.birthDate}
            onChange={updateField}
            required
          />

          <label htmlFor="email">Email</label>
          <input
            id="email"
            name="email"
            type="email"
            autoComplete="email"
            value={form.email}
            onChange={updateField}
            placeholder="you@example.com"
            required
          />

          <label htmlFor="password">Password</label>
          <input
            id="password"
            name="password"
            type="password"
            autoComplete="new-password"
            value={form.password}
            onChange={updateField}
            minLength={8}
            required
          />

          <label htmlFor="confirmPassword">Confirm password</label>
          <input
            id="confirmPassword"
            name="confirmPassword"
            type="password"
            autoComplete="new-password"
            value={form.confirmPassword}
            onChange={updateField}
            minLength={8}
            required
          />

          {error && <p className="error">{error}</p>}
          {success && <p className="success">{success}</p>}

          <button className="primary-button" disabled={loading}>
            {loading ? "Регистрация..." : "Register"}
          </button>
        </form>

        <p className="auth-footer">
          Уже есть аккаунт? <Link to="/login">Войти</Link>
        </p>
      </section>
    </main>
  );
}
