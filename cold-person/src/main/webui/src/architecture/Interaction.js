import styled from "styled-components"

const InteractionDisplay = styled.div`
  align-self: center;
  background: transparent;
  padding: 1rem 1rem;
  margin: 1rem;
  transition: all .5s ease;
  color: #41403E;
  font-size: 2rem;
  letter-spacing: 1px;
  outline: none;
  box-shadow: 2px 8px 4px -6px hsla(0, 0%, 0%, .3);

  border-top-left-radius: 255px 25px;
  border-top-right-radius: 15px 225px;
  border-bottom-right-radius: 225px 15px;
  border-bottom-left-radius: 15px 255px;

  /*
  Above is shorthand for:
  border-top-left-radius: 255px 15px;
  border-top-right-radius: 15px 225px;
  border-bottom-right-radius: 225px 15px;
  border-bottom-left-radius:15px 255px;
  */

  &:hover {
    box-shadow: 20px 38px 34px -26px hsla(0, 0%, 0%, .2);
  }

  border: solid 4px #41403E;


`

const Interaction = ({interaction}) => {
    return (

        <InteractionDisplay>
            {interaction.methodName}
        </InteractionDisplay>
    );
};

export default Interaction;
