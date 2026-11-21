
const API_URL = "http://localhost:8080";

export async function apiRequest(path, options = {}) {
  const token = sessionStorage.getItem("token");

  const headers = {
    "Content-Type": "application/json",
    ...(options.headers || {}),
  };

  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`${API_URL}${path}`, {
    ...options,
    headers,
  });

  const text = await response.text();
  let data = null;

  try {
    data = text ? JSON.parse(text) : null;
  } catch {
    data = text;
  }

  if (!response.ok) {
    if (response.status === 401 && token) {
      sessionStorage.removeItem("token");
      sessionStorage.removeItem("user");
    }

    throw new Error(
      data?.message || data?.detail || `Ошибка HTTP ${response.status}`
    );
  }

  return data;
}
