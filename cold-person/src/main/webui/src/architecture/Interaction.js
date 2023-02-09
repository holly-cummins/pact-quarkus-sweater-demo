import styled from "styled-components"
import {useEffect, useState} from "react";
import rough from "roughjs/bundled/rough.cjs.js";

const arrowWidth = 180;

const InteractionDisplay = styled.div`
  display: flex;
  flex-direction: row;

  &:hover {
    z-index: 1;
  }
`

const Event = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  font-size: 1rem;
  font-style: italic;

`

const Component = styled.div`
  align-self: center;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background: transparent;
  height: 5rem;
  transition: all .5s ease;
  font-size: 1.25rem;
  text-align: center;
  letter-spacing: 1px;
  outline: none;
`

const Anchor = styled.div`
  position: relative;
  width: ${arrowWidth}px;
  height: 0;
`

// If we don't set a height, this will take the width of the parent anchor, which is what we want
const Rough = styled.svg`
  position: absolute;
  left: 0;
  transform: ${props => props.center ? "translateY(-50%)" : "translateY(3px)"};

  ${props => props.shadow && `
    -webkit-filter: drop-shadow(1px 1px 1px rgba(0, 0, 0, .7));
  filter: drop-shadow(1px 1px 1px rgba(0, 0, 0, .7));

  &:hover {
    z-index: 2;
    opacity: 0.2;
    -webkit-filter: drop-shadow(3px 3px 2px rgba(0, 0, 0, .7));
    filter: drop-shadow(3px 3px 2px rgba(0, 0, 0, .7));
  }
  `}

  ${props => props.reverse && `
  -webkit-transform: scaleX(-1);
   transform: scaleX(-1);
  `}
`
// This height adjustment is super-fragile and hand-tuned in the non-centering case, and depends on the relationship between the aspect ratio of the viewbox and the parent dimensions


const Payload = styled.pre`
  position: absolute;
  opacity: 0.9;
  padding: 1rem;
  background-color: white;
  font-size: 1.5rem;
  font-family: Monaco, Courier, monospace;
  border-radius: 255px 15px 225px 15px/15px 225px 15px 255px;
  box-shadow: rgba(0, 0, 0, 0.35) 0 5px 15px;

  transform: translateY(-40px);
  ${props => props.reverse && `
  transform: translateY(30px);
  `}

`


const MethodName = styled.div`
  height: 20px
`

const eventLine = (id, isException) => {
    const svg = document.getElementById(id);
    const rc = rough.svg(svg);
    const node = rc.line(10, 0, 90, 0, {stroke: isException ? "crimson" : "black"});
    svg.appendChild(node);
    const arrowHead = rc.path("M85,-5l5,5l-5,5", {stroke: isException ? "crimson" : "black"})
    svg.appendChild(arrowHead)

}

const componentBox = (id) => {
    const svg = document.getElementById(id);
    const rc = rough.svg(svg);
    const node = rc.rectangle(10, 10, 180, 60);
    svg.appendChild(node);
}

const stringify = (payload) => JSON.stringify(payload, null, 2)


const Interaction = ({request, response}) => {
    const requestSvg = "request-svg" + request?.id;
    const responseSvg = "response-svg" + response?.id;
    const componentSvg = "component-svg" + request?.id
    const isException = request?.payload?.exception != null;

    useEffect(() => {
        request && eventLine(requestSvg, isException)
        response && eventLine(responseSvg, isException)

        componentBox(componentSvg)

    }, [request, response, requestSvg, responseSvg, componentSvg, isException])

    const [isRequestOpen, setRequestOpen] = useState(false)
    const [isResponseOpen, setResponseOpen] = useState(false)

    const handleRequestOpen = () => {
        setRequestOpen(true)
    }

    const handleRequestClose = () => {
        setRequestOpen(false)
    }

    const handleResponseOpen = () => {
        setResponseOpen(true)
    }

    const handleResponseClose = () => {
        setResponseOpen(false)
    }

    return (

        <InteractionDisplay>
            <Event isException={isException}>
                <MethodName> {request?.methodName}</MethodName>
                <Anchor>
                    <Rough shadow={true} onMouseOver={handleRequestOpen}
                           onMouseOut={handleRequestClose} data-testid="request-line" center={true} id={requestSvg}
                           viewBox="0 -15 100 30">
                    </Rough>
                </Anchor>
                <Anchor>
                    <Rough shadow={true} onMouseOver={handleResponseOpen}
                           onMouseOut={handleResponseClose} data-testid="response-line" center={true} reverse={true}
                           id={responseSvg}
                           viewBox="0 -15 100 30">
                    </Rough>
                </Anchor>
                <MethodName/> {/*Cheat and add centring padding with a div*/}
            </Event>


            <Component>

                <Anchor>
                    <Rough id={componentSvg} viewBox="0 0 200 80">
                    </Rough>
                </Anchor>
                {request?.owningComponent}
                <Anchor/>
            </Component>

            {isRequestOpen && (<Payload>
                {stringify(request?.payload)}
            </Payload>)}
            {isResponseOpen && (<Payload reverse={true}>
                {stringify(response?.payload)}
            </Payload>)}
        </InteractionDisplay>
    );
};

export default Interaction;
