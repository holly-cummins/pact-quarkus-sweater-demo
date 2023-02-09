import styled from "styled-components"
import {useEffect, useState} from "react";
import rough from "roughjs/bundled/rough.cjs.js";

const roughness = 2.8
const componentHeight = 90;
const componentWidth = 80;
const canvasPadding = 20;
const arrowWidth = 180;

const InteractionDisplay = styled.div`
  display: flex;
  flex-direction: row;
`

const Event = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: grey;
  width: 200px;
  align-items: center;
`

const Component = styled.div`
  align-self: center;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background: transparent;
  height: 5rem;
  margin: 1rem;
  transition: all .5s ease;
  border: 1px solid deeppink;
  font-size: 1.25rem;
  text-align: center;
  letter-spacing: 1px;
  outline: none;
  box-shadow: 2px 8px 4px -6px hsla(0, 0%, 0%, .3);

  &:hover {
    box-shadow: 20px 38px 34px -26px hsla(0, 0%, 0%, .2);
  }

`

const Payload = styled.div`
  position: absolute;
  opacity: 0.8;
  background-color: white;
`

const Anchor = styled.div`
  position: relative;
  border: 1px solid blue;
  width: ${arrowWidth}px;
  height: 5px;
`

const Rough = styled.svg`
  border: 1px solid red;
  position: absolute;
  left: 0;
  top: -5px;
`

// If we don't set a height, this will take the width of the parent anchor, which is what we want
const CentredRough = styled.svg`
  border: 1px solid red;
  position: absolute;
  left: 0;
  transform: translateY(-50%);
`

const MethodName = styled.div`
  border: 1px solid yellow;

  height: 20px
`

const Interaction = ({interaction}) => {
    const eventSvg = "event-svg" + interaction.payload.orderNumber + interaction.methodName;
    const componentSvg = "component-svg" + interaction.payload.orderNumber + interaction.owningComponent

    useEffect(() => {
        let svg = document.getElementById(eventSvg);
        let rc = rough.svg(svg);
        let node = rc.line(10, 10, 90, 10);
        svg.appendChild(node);

        svg = document.getElementById(componentSvg);
        rc = rough.svg(svg);
        node = rc.rectangle(10, 10, 180, 80);
        svg.appendChild(node);
    }, [])

    const [isOpen, setOpen] = useState(false)

    const handleOpen = () => {
        //setOpen(true)
    }

    const handleClose = () => {
        setOpen(false)
    }

    const isException = interaction.payload.exception != null;

    console.log("rendering", interaction.payload.orderNumber, interaction.owningComponent)
    return (

        <InteractionDisplay onMouseOver={handleOpen}
                            onMouseOut={handleClose} isException={isException}>
            <Event>


                <MethodName> {interaction.methodName}</MethodName>
                <Anchor id={"some-id"}>
                    <CentredRough id={eventSvg} viewBox="0 0 100 20">
                        <circle cx={50} cy={50} r={10} fill="red"/>
                    </CentredRough>
                </Anchor>
                <MethodName/> {/*Cheat and add centreing padding with a div*/}
            </Event>


            <Component>

                <Anchor>

                    <Rough id={componentSvg} viewBox="0 0 200 100">
                    </Rough>
                </Anchor>
                {interaction.owningComponent}
                <Anchor/>
            </Component>

            {isOpen && (<Payload>
                {JSON.stringify(interaction.payload)}
            </Payload>)}
        </InteractionDisplay>
    );
};

export default Interaction;
