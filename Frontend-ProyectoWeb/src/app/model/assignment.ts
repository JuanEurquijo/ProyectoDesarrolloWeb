import { Bus } from "./bus";
import { Driver } from "./driver";
import { Route } from "./route";
import { Schedule } from "./schedule";


export class Assignment {
  public id:number | undefined;
  constructor(
    public driver?:Driver,
    public bus?:Bus,
    public route?:Route,
    public schedule?:Schedule) {};

}
