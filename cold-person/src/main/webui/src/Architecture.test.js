import React from "react";
import {render, screen} from "@testing-library/react";

import Architecture from "./Architecture";

import axios from "axios";

// Workaround for CORS complaints; see https://github.com/axios/axios/issues/2654
axios.defaults.adapter = require("axios/lib/adapters/http");
jest.mock("axios");

describe("the architecture view", () => {
    const data = [{"name": "widget"}, {"name": "splodger"}, {"name": "fizzwhuzz"}];

    beforeEach(() => {
        axios.get.mockResolvedValue({data});
    });

    test("includes all the component names", async () => {
        render(<Architecture/>);
        let el = await screen.findByText(/fizzwhuzz/i);
        expect(el).toBeInTheDocument();

        el = await screen.findByText(/splodger/i);
        expect(el).toBeInTheDocument();

        el = await screen.findByText(/widget/i);
        expect(el).toBeInTheDocument();
    });


});
