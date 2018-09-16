import React, {Component} from 'react'
import InputData from "./input-data/InputData";
import Result from "./results/Result";
import {Button, Container, Grid} from "semantic-ui-react";
import ProgressHeader from "./progress-header/ProgressHeader";




class CsvToDxf extends Component {
    constructor(props) {
        super(props);
        this.handleConvertResponse = this.handleConvertResponse.bind(this);
        this.progressAhead = this.progressAhead.bind(this);
        this.finishStep = this.finishStep.bind(this);
        this.handleProgress = this.handleProgress.bind(this);
        this.restartConverter = this.restartConverter.bind(this);
        this.state = this.getInitialState();
    }

    getInitialState = () => {
        const initialState = {
            convertResponse: '',
            isConvertDone: false,
            step1IsDone: false,
            step1Active: true,
            step1Disabled: false,
            step2IsDone: false,
            step2Active: false,
            step2Disabled: true,
            step3IsDone: false,
            step3Active: false,
            step3Disabled: true,
        };

        return initialState;
    };

    handleConvertResponse(response) {
        this.setState({
            isConvertDone: true,
            convertResponse: response,

        });
        this.handleProgress(3);
    }

    handleProgress(stepNumber) {
        console.log('called');
        const MAX_NUMBER_OF_STEPS = 3;
        if (stepNumber <= MAX_NUMBER_OF_STEPS) {
            this.finishStep(stepNumber - 1);
            this.progressAhead(stepNumber);
        }
    };



    progressAhead(stepNumber) {
        this.setState({
            [`step${stepNumber}Active`]: true,
            [`step${stepNumber}IsDone`]: false,
            [`step${stepNumber}Disabled`]: false,
        })
    };

    finishStep(stepNumber) {
        this.setState({
            [`step${stepNumber}Active`]: false,
            [`step${stepNumber}IsDone`]: true,
            [`step${stepNumber}Disabled`]: false,
        })
    };

    restartConverter() {
        console.log("resetting from main component");
        this.resetThisState();
        this.inputData.resetState();
    }

    resetThisState() {
        this.setState(this.getInitialState());
    }

    render() {
        const convertResponse = this.state.convertResponse;
        let isConvertComplete = this.state.isConvertDone;
        let reset = this.state.reset;
        let result;
        console.log(isConvertComplete);
        console.log(this.state);

        if(isConvertComplete) {
            console.log("From here");
            result = <Result convertResponse={convertResponse}/>
        }
        let step1 = {active : this.state.step1Active, isDone: this.state.step1IsDone, disabled: this.state.step1Disabled};
        let step2 = {active: this.state.step2Active, isDone: this.state.step2IsDone, disabled: this.state.step2Disabled};
        let step3 = {active: this.state.step3Active, isDone: this.state.step3IsDone, disabled: this.state.step3Disabled};

        return (
            <Container textAlign='left' style={{width : 700}}>
                <h2>CSV to DXF converter</h2>
                <h4>Convert csv survey data to Cad drawing</h4>
                <Grid>
                    <Grid.Row>
                        <ProgressHeader stepOne={step1} stepTwo={step2} stepThree={step3}/>
                    </Grid.Row>
                    <Grid.Row>
                        <InputData ref={inputData => this.inputData = inputData} onReset={reset} onProgress={this.handleProgress} onConvertResponse={this.handleConvertResponse}/>
                        {result}
                    </Grid.Row>
                </Grid>
                <Button onClick={this.restartConverter}>New Convert</Button>
            </Container>
        );

    }
}

export default CsvToDxf;