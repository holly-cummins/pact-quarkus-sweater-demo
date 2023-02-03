import React from "react";
import { render, screen } from "@testing-library/react";
import App from "./App";
import "@testing-library/jest-dom";

test("renders a suitable title", () => {
  render(<App />);
  const titleElement = screen.getByText(/sweater shop/i);
  expect(titleElement).toBeInTheDocument();
});
