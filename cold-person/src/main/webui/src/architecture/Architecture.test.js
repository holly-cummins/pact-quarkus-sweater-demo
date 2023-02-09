import React from "react";
import {render, screen} from "@testing-library/react";

import Architecture from "./Architecture";

import axios from "axios";

// Workaround for CORS complaints; see https://github.com/axios/axios/issues/2654
axios.defaults.adapter = require("axios/lib/adapters/http");
jest.mock("axios");

describe("the architecture view", () => {

    // This is testing a bit too much of the components and interactions components, but we can live with it for the moment
    const data = [{"name": "widget", id: "1"}, {"name": "splodger", id: "2"}, {"name": "fizzwhuzz", id: "3"}];

    beforeEach(() => {
        axios.get.mockResolvedValue({data});
    });

    // Components have been temporarily removed
    xtest("includes all the component names", async () => {
        render(<Architecture/>);
        let el = await screen.findByText(/fizzwhuzz/i);
        expect(el).toBeInTheDocument();

        el = await screen.findByText(/splodger/i);
        expect(el).toBeInTheDocument();

        el = await screen.findByText(/widget/i);
        expect(el).toBeInTheDocument();
    });


});
