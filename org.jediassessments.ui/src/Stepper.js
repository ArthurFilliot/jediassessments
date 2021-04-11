import React, { useEffect, useState } from 'react'
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';
import StepContent from '@material-ui/core/StepContent';
import Typography from '@material-ui/core/Typography';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';

function getSteps() {
  return ['Select campaign settings', 'Create an ad group', 'Create an ad','','','','','','','',''];
}

function getStepContent(step) {
  switch (step) {
    case 0:
      return `For each ad campaign that you create, you can control how much
              you're willing to spend on clicks and conversions, which networks
              and geographical locations you want your ads to show on, and more.`;
    case 1:
      return 'An ad group contains one or more ads which target a shared set of keywords.';
    case 2:
      return `Try out different ad text to see what brings in the most customers,
              and learn how to enhance your ads using features like ad extensions.
              If you run into any problems with your ads, find out how to tell if
              they're running and how to resolve approval issues.`;
    default:
      return `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
              Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 
              Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
              Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.`;
  }
}

let dates = {
  1: 'aaaaa',
  2: 'bbbbb',
  3: '',
  4: '',
  5: '',
  6: '',
  7: '',
  8: '',
  9: '',
  10: '',
  11: '',
};

const GalacticDateIndent=(props)=> {
  const {active, completed, icon} = props;
  let useStyles;
  const classes = (useStyles = makeStyles({
    root: {
      color: '#eaeaf0',
      display: 'flex',
      height: 22,
      alignItems: 'center',
    },
    active: {
      color: '#784af4',
    },
    future: {
      color: '#784af4',
    },
    passed: {
      color: '#784af4',
    },
  }))();
  
  return (
    <div className={clsx(classes.root, {[classes.active]: active, [classes.passed]: completed, })}>
      {icon} : {dates[icon]}
    </div>
  );
}

const VerticalLinearStepper = (props) => {
  const [currentWindow, setCurrentWindow] = useState(null);
  const [activeStep, setActiveStep] = useState(0);
  const [listening, setListening] = useState(false);

  let useStyles;
  const classes = (useStyles = makeStyles((theme) => ({
    root: {
      width: '100%',
    },
    button: {
      marginTop: theme.spacing(1),
      marginRight: theme.spacing(1),
    },
    actionsContainer: {
      marginBottom: theme.spacing(2),
    },
    resetContainer: {
      padding: theme.spacing(3),
    },
  })))();
  
  let eventSource;
  useEffect(() => {
      if (!listening) {
          eventSource = new EventSource('/galacticstandardcalendar/now/11');
          console.log(eventSource);
          eventSource.onmessage = (event) => {
              console.debug("VerticalLinearStepper Event fired");
              let edates = JSON.parse(event.data).dates;
              let i = 0;
              let activeIndex=0;
              edates.forEach(entry => {
                dates[i+1] = JSON.stringify(entry.key);
                if (entry.value==='Active') {
                  activeIndex=i;
                }
                i++;
              });
              console.debug("VerticalLinearStepper activeIndex : " + activeIndex);
              setActiveStep(activeIndex);
          }
          eventSource.onerror = (err) => {
              console.error("EventSource failed:", err);
              eventSource.close();
          }
          setListening(true)
      }
      return () => {
              eventSource.close();
              console.log("event closed")
      }

  }, [currentWindow]);
  
  const steps = getSteps();

  return (
    <div className={classes.root}>
      <Stepper activeStep={activeStep} orientation="vertical" >
        {steps.map((label, index) => (
          <Step key={index}>
            <StepLabel StepIconComponent={GalacticDateIndent}>{label}</StepLabel>
            <StepContent>
              <Typography>{getStepContent(index)}</Typography>
            </StepContent>
          </Step>
        ))}
      </Stepper>
    </div>
  );
};

export default VerticalLinearStepper;