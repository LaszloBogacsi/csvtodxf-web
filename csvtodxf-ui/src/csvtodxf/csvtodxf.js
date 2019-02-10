import React, {Component} from 'react'
import InputData from "./input-data/InputData";
import Result from "./results/Result";
import {Button, Container, Dimmer, Grid, Loader, Segment} from "semantic-ui-react";
import ProgressHeader from "./progress-header/ProgressHeader";
import {httpModule as http} from "./shared/httpModule";
import MessageDisplayer from "./shared/messageDisplayer";


class CsvToDxf extends Component {
    constructor(props) {
        super(props);
        this.handleConvertResponse = this.handleConvertResponse.bind(this);
        this.progressAhead = this.progressAhead.bind(this);
        this.finishStep = this.finishStep.bind(this);
        this.handleProgress = this.handleProgress.bind(this);
        this.restartConverter = this.restartConverter.bind(this);
        this.toggleLoader = this.toggleLoader.bind(this);
        this.handleFileNameChange = this.handleFileNameChange.bind(this);
        this.displayMessage = this.displayMessage.bind(this);
        this.state = this.getInitialState();
    }

    getInitialState = () => {
        const initialState = {
            convertResponse: '',
            isConvertDone: false,
            loaderActive: false,
            message: {},

            step1IsDone: false,
            step1Active: true,
            step1Disabled: false,
            step1FileName: '',
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
        let retryCount = 0;
        this.checkConvertStatus(response.response, retryCount);

    }

    handleFileNameChange(fileName) {
        this.setState({step1FileName: fileName})
    }

    checkConvertStatus(convertJobId, numOfretries) {
        const url = `/convert/status/${convertJobId}`;
        http.get(url, {
            headers: {
                'Accept': 'application/json',
            }
        }).then(response => {
            setTimeout(() => {
                if (response.data.jobResult === 'SUCCESS') {
                    this.setState({
                        isConvertDone: true,
                        convertResponse: response.data,

                    });
                    this.toggleLoader();
                    this.handleProgress(3);
                } else if (numOfretries < 5) {
                    const numOfTries = numOfretries + 1;
                    this.checkConvertStatus(convertJobId, numOfTries); // recursive
                } else {
                    this.toggleLoader();
                    this.displayMessage({header: "Ooops...", content: "Something went wrong please retry"});
                }
            }, 2000)

        })
            .catch(error => {
                this.displayMessage({header: "error", content: error.toString()});

            })
    }

    toggleLoader() {
        this.setState({loaderActive: !this.state.loaderActive})
    }

    handleProgress(stepNumber) {
        const MAX_NUMBER_OF_STEPS = 3;
        if (stepNumber <= MAX_NUMBER_OF_STEPS) {
            this.finishStep(stepNumber - 1);
            this.progressAhead(stepNumber);
        }
        if (stepNumber === MAX_NUMBER_OF_STEPS + 1) {
            this.finishStep(stepNumber - 1);
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
        this.resetThisState();
        if (this.inputData) this.inputData.resetState();
    }

    resetThisState() {
        this.setState(this.getInitialState());
    }

    displayMessage(message) {
        this.setState({message: message})
    }

    render() {
        const convertResponse = this.state.convertResponse;
        let isConvertComplete = this.state.isConvertDone;
        let loaderActive = this.state.loaderActive;
        let message = this.state.message;
        let step1 = {
            active: this.state.step1Active,
            isDone: this.state.step1IsDone,
            disabled: this.state.step1Disabled,
            fileName: this.state.step1FileName
        };
        let step2 = {
            active: this.state.step2Active,
            isDone: this.state.step2IsDone,
            disabled: this.state.step2Disabled
        };
        let step3 = {
            active: this.state.step3Active,
            isDone: this.state.step3IsDone,
            disabled: this.state.step3Disabled
        };

        return (
            <Container textAlign='left' style={{width: 700}}>
                <h2>CSV to DXF converter</h2>
                <h4>Convert csv survey data to Cad drawing</h4>
                <Grid>
                    <Grid.Row>
                        <ProgressHeader stepOne={step1} stepTwo={step2} stepThree={step3}/>
                    </Grid.Row>
                    <Grid.Row>
                        <Container>
                            <MessageDisplayer message={message}/>
                            <Segment raised>
                                <Dimmer active={loaderActive}>
                                    <Loader content='Loading'/>
                                </Dimmer>
                                {!isConvertComplete ? (
                                    <InputData ref={inputData => this.inputData = inputData}
                                               onToggleLoader={this.toggleLoader}
                                               onProgress={this.handleProgress}
                                               onConvertResponse={this.handleConvertResponse}
                                               onFileNameChange={this.handleFileNameChange}
                                               onError={this.displayMessage}/>
                                ) : (
                                    <Result convertResponse={convertResponse} onProgress={this.handleProgress}/>
                                )}
                            </Segment>
                            <Button onClick={this.restartConverter}>New Convert</Button>
                        </Container>
                    </Grid.Row>
                </Grid>
            </Container>
        );
    }
}

export default CsvToDxf;