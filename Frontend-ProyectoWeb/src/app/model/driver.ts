import { Bus } from "./bus";

export class Driver {
  public id: number | undefined;
  public assignedBuses:Bus[]
  constructor(
    public name:string,
    public lastName:string,
    public identifier:Number | undefined,
    public phone:Number | undefined,
    public address:string) {};

}
