import React from "react";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";

import Button from "./Button";

import axios from "axios";
import { act } from "react-dom/test-utils";

// Workaround for CORS complaints; see https://github.com/axios/axios/issues/2654
axios.defaults.adapter = require("axios/lib/adapters/http");
jest.mock("axios");

describe("the order sweater button", () => {
  const expectedResult = { colour: "white", size: 10 };
  const user = userEvent.setup();

  beforeEach(() => {
    axios.post.mockResolvedValue({ data: expectedResult });
  });

  test("includes an order button", async () => {
    render(<Button />);
    const el = await screen.findByText(/order/i);
    expect(el).toBeTruthy();
  });

  test("includes an colour field", async () => {
    render(<Button />);
    const input = await screen.findByRole("textbox");
    expect(input).toBeInTheDocument();
  });

  test("sends a request when the button is clicked", async () => {
    render(<Button />);
    await act(async () => {
      await user.click(screen.getByRole("button"));
    });

    expect(axios.post).toHaveBeenCalled();
  });

  test("sends the requested colour when the button is clicked", async () => {
    render(<Button />);
    const colour = "magenta";
    const input = await screen.findByRole("textbox");
    await act(async () => {
      await user.type(input, colour);
      await user.click(screen.getByRole("button"));
    });
    expect(axios.post).toHaveBeenCalledWith(
      expect.anything(),
      expect.objectContaining({ colour }),
      expect.anything()
    );
  });
});
