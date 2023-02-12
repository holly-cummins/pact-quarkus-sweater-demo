import React from "react";
import {render, screen} from "@testing-library/react";
import OrderSequence from "./OrderSequence";
import userEvent from "@testing-library/user-event";


describe("the order sequence", () => {
    const orderNumber = 16;
    const name = "frog"
    const component1 = "component1"
    const component2 = "component2"
    const component3 = "component3"

    const interaction1 = {
        methodName: name + 1,
        type: "request",
        owningComponent: component1,
        id: 1,
        payload: {orderNumber},
        timestamp: 10
    };
    const interaction1a = {
        methodName: "other" + name,
        type: "response",
        owningComponent: component1,
        id: 11,
        payload: {orderNumber},
        timestamp: 6
    };
    const interaction2 = {
        methodName: name + 2,
        type: "Request",
        owningComponent: component2,
        id: 12,
        payload: {orderNumber},
        timestamp: 7
    };
    const interaction3 = {
        methodName: name + 3,
        type: "request",
        owningComponent: component3,
        payload: null,
        id: 13,
        timestamp: 2
    };
    const irrelevantInteraction = {
        methodName: name + 4, type: "request",
        owningComponent: component3,
        id: 13,
        payload: {orderNumber: 1}
    };
    const payloadString = "some interesting distinctive content"
    const duplicate = {
        type: "request",
        methodName: null,
        owningComponent: component3,
        id: 13,
        payload: {something: payloadString},
        timestamp: 2
    };

    const orderSequence = [interaction1, interaction2, interaction1a, interaction3];


    test("displays the order number", async () => {
        render(<OrderSequence orderNumber={orderNumber} interactions={orderSequence}/>);
        const el = await screen.findByText(/16/);
        expect(el).toBeInTheDocument();
    });

    test("filters out interactions with a different order number", async () => {
        render(<OrderSequence orderNumber={orderNumber} interactions={orderSequence}/>);
        const el = screen.queryByText(irrelevantInteraction.methodName);
        expect(el).not.toBeInTheDocument();
    });

    test("displays the component name", async () => {
        // Check both the easy cases where there is no grouping needed
        render(<OrderSequence orderNumber={orderNumber} interactions={orderSequence}/>);
        const el2 = await screen.findByText(component2);
        expect(el2).toBeInTheDocument();

        const el3 = await screen.findByText(component3);
        expect(el3).toBeInTheDocument();
    });

    test("groups interactions by owning component", async () => {
        render(<OrderSequence orderNumber={orderNumber} interactions={orderSequence}/>);
        const el1s = await screen.findAllByText(component1);
        expect(el1s.length).toBe(1);
    });

    test("sorts components by request timestamp", async () => {
        render(<OrderSequence orderNumber={orderNumber} interactions={orderSequence}/>);
        // This checks each element is in the page once
        let el1 = await screen.findByText(component1);
        expect(el1).toBeInTheDocument();

        const el2 = await screen.findByText(component2);
        expect(el2).toBeInTheDocument();

        const el3 = await screen.findByText(component3);
        expect(el3).toBeInTheDocument();


        // The document position return is a bitwise mask

        // Order number 2 should follow order 3
        expect(el3.compareDocumentPosition(el2) & Node.DOCUMENT_POSITION_FOLLOWING).toBeTruthy();

        // Order number 1 should follow order 3
        expect(el3.compareDocumentPosition(el1) & Node.DOCUMENT_POSITION_FOLLOWING).toBeTruthy();

        // Order number 2 should follow order 3
        expect(el2.compareDocumentPosition(el1) & Node.DOCUMENT_POSITION_FOLLOWING).toBeTruthy();

    });

    test("consolidates multiple interactions of the same type", async () => {
        const user = userEvent.setup()
        render(<OrderSequence orderNumber={orderNumber} interactions={[duplicate, interaction3]}/>);

        const el3 = await screen.findByText(component3);
        expect(el3).toBeInTheDocument();

// We should have the payload of the duplicate and the method name of the original
        await user.hover(screen.getByTestId("request-line"))
        const el = await screen.findByText(payloadString, {exact: false});
        expect(el).toBeInTheDocument();

        const el2 = await screen.findByText(interaction3.methodName)
        expect(el2).toBeInTheDocument();
    });


});
