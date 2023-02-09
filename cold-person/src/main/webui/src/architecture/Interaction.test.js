import React from "react";
import {render, screen} from "@testing-library/react";
import Interaction from "./Interaction";

import userEvent from "@testing-library/user-event"


describe("the interaction", () => {
    const payload = {fred: "mabel"};
    const payloadString = /mabel/;
    const methodName = "doThing"
    const componentName = "widget"

    const interaction = {methodName: methodName, owningComponent: componentName, payload};
    let user;

    beforeEach(() => {
        user = userEvent.setup()

    })

    describe("with a request only", () => {

        test("displays a method name", async () => {
            render(<Interaction request={interaction}/>);
            const element = await screen.findByText(methodName);
            expect(element).toBeInTheDocument();
        });

        test("displays a component name", async () => {
            render(<Interaction request={interaction}/>);
            const element = await screen.findByText(componentName);
            expect(element).toBeInTheDocument();
        });

        it("hides the payload before any hover", async () => {
            render(<Interaction request={interaction}/>);
            expect(screen.queryByText(payload)).toBeNull()
        })

        it("hovering on the name brings up a payload", async () => {
            render(<Interaction request={interaction}/>);
            await user.hover(screen.getByText(methodName))
            expect(screen.getByText(payloadString)).toBeInTheDocument();
        })

        it("leaving the item after hovering hides the payload", async () => {
            const dummyElement = "dummy element"
            render(<div>
                <div>{dummyElement}</div>
                <Interaction request={interaction}/></div>);

            await user.hover(screen.getByText(methodName))
            expect(screen.getByText(payloadString)).toBeInTheDocument();

            await user.hover(screen.getByText(dummyElement))
            expect(screen.queryByText(payloadString)).toBeNull()

        })
    })

    describe("with a request and a response", () => {
        const responsePayload = {alice: "bob"};
        const responsePayloadString = /bob/;
        const response = {methodName: "whatever", owningComponent: componentName, payload: responsePayload};


        test("displays a method name", async () => {
            render(<Interaction request={interaction} response={response}/>);
            const element = await screen.findByText(methodName);
            expect(element).toBeInTheDocument();
        });

        it("does not display the response method name", async () => {
            render(<Interaction request={interaction} response={response}/>);
            expect(screen.queryByText(response.methodName)).toBeNull()
        })


        test("displays a component name", async () => {
            render(<Interaction request={interaction} response={response}/>);
            const element = await screen.findByText(componentName);
            expect(element).toBeInTheDocument();
        });

        it("hides the payload before any hover", async () => {
            render(<Interaction request={interaction} response={response}/>);
            expect(screen.queryByText(payload)).toBeNull()
        })

        it("hovering on the name brings up a payload", async () => {
            render(<Interaction request={interaction} response={response}/>);

            await user.hover(screen.getByTestId("response-line"))
            expect(screen.getByText(responsePayloadString)).toBeInTheDocument();
        })

        it("leaving the item after hovering hides the payload", async () => {
            const dummyElement = "dummy element"
            render(<div>
                <div>{dummyElement}</div>
                <Interaction request={interaction} response={response}/></div>);

            await user.hover(screen.getByTestId("response-line"))
            expect(screen.getByText(responsePayloadString)).toBeInTheDocument();

            await user.hover(screen.getByText(dummyElement))
            expect(screen.queryByText(responsePayloadString)).toBeNull()

        })
    })
})

