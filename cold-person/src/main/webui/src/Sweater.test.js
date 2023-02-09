import React from "react";
import {render, screen} from "@testing-library/react";

import Sweater from "./Sweater";


describe("the Sweater", () => {
    const sweater = {colour: "pink", size: 10, orderNumber: 5};

    test("displays a colour", async () => {
        render(<Sweater sweater={sweater}/>);
        const colour = await screen.findByText(sweater.colour, {exact: false});
        expect(colour).toBeInTheDocument();
    });

    test("displays an order number", async () => {
        render(<Sweater sweater={sweater}/>);
        const colour = await screen.findByText(sweater.orderNumber, {exact: false});
        expect(colour).toBeInTheDocument();
    });
});
