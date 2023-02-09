import React from "react";
import {render, screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";

import Button from "./Button";

import axios from "axios";

// Workaround for CORS complaints; see https://github.com/axios/axios/issues/2654
axios.defaults.adapter = require("axios/lib/adapters/http");
jest.mock("axios");

let orderNumber = 1
const incrementedOrderNumber = () => orderNumber++;

describe("the order sweater button", () => {
    const expectedResult = {colour: "white", size: 10, orderNumber: incrementedOrderNumber()};
    const user = userEvent.setup();

    beforeEach(() => {
        axios.post.mockResolvedValue({data: expectedResult});
    });

    test("includes an order button", async () => {
        render(<Button/>);
        const el = await screen.findByText(/order/i);
        expect(el).toBeTruthy();
    });

    test("includes a colour field", async () => {
        render(<Button/>);
        const input = await screen.findByRole("textbox");
        expect(input).toBeInTheDocument();
    });

    test("sends a request when the button is clicked", async () => {
        render(<Button/>);
        await user.click(screen.getByRole("button"));

        expect(axios.post).toHaveBeenCalled();
    });

    test("sends the requested colour when the button is clicked", async () => {
        render(<Button/>);
        const colour = "magenta";
        const input = await screen.findByRole("textbox");
        await user.type(input, colour);
        await user.click(screen.getByRole("button"));

        expect(axios.post).toHaveBeenCalledWith(
            expect.anything(),
            expect.objectContaining({colour}),
            expect.anything()
        );
    });

});
