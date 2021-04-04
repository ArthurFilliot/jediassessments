import React,{Component, useState, useEffect} from 'react';
import VerticalLinearStepper from './Stepper'; // Import a component from another file

export default class App extends Component {

  constructor(props) {
    super(props)
    this.state = {
      nbTry: 1
    }
  }

  getNbTry=()=> {
    return this.state.nbTry;
  }

  setNbTry=(nb)=> {
    this.setState({nbTry:this.state.nbTry+1})
  }
  
  render=() => {
    return (
      <VerticalLinearStepper />
    )
  }

}